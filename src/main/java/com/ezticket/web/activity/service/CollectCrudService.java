package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.Collect;
import com.ezticket.web.activity.pojo.CollectRedis;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.pojo.TorderDetailsView;
import com.ezticket.web.activity.repository.CollectRedisRepository;
import com.ezticket.web.activity.repository.CollectRepository;
import com.ezticket.web.activity.repository.TorderDetailsViewRepository;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CollectCrudService {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private CollectRedisRepository collectRedisRepository;
    @Autowired
    private TorderDetailsViewRepository tdvRepository;
    @Autowired
    private TorderDetailsViewService torderDetailsViewService;

    @Autowired
    private MemberRepository memberRepository;

    CollectService collectService = new CollectService();
    private final ResourceLoader resourceLoader;

    @Value("${checkin.ip}")
    private String checkinip;

    public CollectCrudService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    //    新增
    @Transactional
    public boolean insertCollect(Torder torder) {
        Integer torderno = torder.getTorderNo();
        //    	該筆訂單所有明細
        List<TorderDetailsView> newDetails = torderDetailsViewService.findAllBytorderNo(torderno);
        for (int i = 0; i < newDetails.size(); i++) {
//      取訂單新增後的其中一筆 tdetail
            TorderDetailsView td = newDetails.get(i);
//        	一筆明細有幾張票
            Integer ticketnum = td.getTqty();
//            j為一筆明細的票券數量
            for (int j = 0; j < ticketnum; j++) {
//              新增一張票進 MySQL
                Collect collect = new Collect();
                CollectRedis collectRedis = new CollectRedis();
                collect.setMemberno(td.getMemberNo());
                collect.setTdetailsno(td.getTdetailsNo());
                collect.setTstatus(0);
                Collect updated = collectRepository.save(collect);
                String updatedCollectno = updated.getCollectno().toString();
//               新增一張票進 Redis
                collectRedis.setCollectno(updatedCollectno);
                collectRedis.setTstatus("0");
                String qrcodeImg = "empty";
                try {
                    qrcodeImg = urlToBase64(updatedCollectno);
                } catch (IOException | WriterException ioe) {
                    ioe.printStackTrace();
                    return false;
                }
                collectRedis.setQrcode(qrcodeImg);
//                票券到期時間
//                時間要計算一下，只能設定從現在開始的差異時間
                Timestamp current = new Timestamp(System.currentTimeMillis());
                long timeDiff = (td.getSessioneTime().getTime() - current.getTime()) / 1000;
                collectRedis.setExpirationInSeconds(timeDiff);
                collectRedisRepository.save(collectRedis);
            }
        }
        return true;
    }

    @Transactional
//  更變 MySQL 票券狀態(-1 取消；0 未使用；1 已使用)
    public boolean changeMysqlStatus(Integer collectno, Integer gstatus) {
        Optional<Collect> optC = collectRepository.findById(collectno);
        if (optC.isEmpty()) {
            System.out.println("MySQL 查無票券");
            return false;
        }
        Collect newCollect = optC.get();
        newCollect.setTstatus(gstatus);
        collectRepository.save(newCollect);
        return true;
    }

    @Transactional
    //  驗票：更變 Redis 票券狀態為1 (狀態：0 未使用；1 已使用，取消直接刪除，不會使用此方法)
    //  回傳代碼：-1 無票券；-2 已使用；-3 票券狀態異常；1 驗票成功
    public int useTicket(Integer collectno) {
        Optional<CollectRedis> optCR = collectRedisRepository.findById(collectno.toString());
        if (optCR.isEmpty()) {
            System.out.println("Redis 查無票券");
            return -1;
        }
        CollectRedis newCR = optCR.get();
        if ("1".equals(newCR.getTstatus())) {
            System.out.println("票券已使用！");
            return -2;
        } else if ("0".equals(newCR.getTstatus())) {
            newCR.setTstatus("1");
            collectRedisRepository.save(newCR);
            return 1;
        } else {
            System.out.println("票券狀態異常");
            return -3;
        }

    }

    //    用來判斷此筆訂單可取消嗎？
//    查詢 Redis 票券狀態，退票前確認用
//    MySQL 僅儲存出票及退票狀態，不須查詢
    public boolean isCancelable(Integer torderNo) {
        List<TorderDetailsView> canceledDetails = torderDetailsViewService.findAllBytorderNo(torderNo);
        List<Integer> cDetailsno = new ArrayList<Integer>();
        for (TorderDetailsView d : canceledDetails) {
//         查出一筆明細編號
            Integer dno = d.getTdetailsNo();
            List<Collect> canceledCollects = collectRepository.findByTdetailsno(dno);
            for (Collect c : canceledCollects) {
//         查出一筆票券編號
                Integer cno = c.getCollectno();
//  查詢 Redis 的該筆票券
                Optional<CollectRedis> optCr = collectRedisRepository.findById(cno.toString());
                if (optCr.isEmpty()) {
                    System.out.println("查無此票。可能場次已過期");
                    return false;
                } else {
                    CollectRedis cr = optCr.get();
                    int crstat = Integer.parseInt(cr.getTstatus());
                    if (crstat == 1) {
                        System.out.println("票券編號 " + cno + " 已使用，此筆訂單不可退票");
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Transactional
//    取消訂單
    public boolean cancelTicket(Integer torderNo) {
//        查詢出所有明細編號
        List<TorderDetailsView> canceledDetails = torderDetailsViewService.findAllBytorderNo(torderNo);
        List<Integer> cDetailsno = new ArrayList<Integer>();
        for (TorderDetailsView d : canceledDetails) {
//         查出一筆明細編號
            Integer dno = d.getTdetailsNo();
            List<Collect> canceledCollects = collectRepository.findByTdetailsno(dno);
            for (Collect c : canceledCollects) {
//         查出一筆票券編號
                Integer cno = c.getCollectno();
                boolean result = changeMysqlStatus(cno, -1);
                System.out.println("MySQL 已改變票券狀態 " + result);
//  刪除 Redis 的該筆票券
                collectRedisRepository.deleteById(cno.toString());
            }
        }
        return true;
    }

    public String urlToBase64(String collectno) throws WriterException, IOException {
//                IP
        StringBuilder urlFrag = new StringBuilder(checkinip);
//                驗票 Controller 的網址
        urlFrag.append("EditCollect/checkin/");
        urlFrag.append(collectno);
        String url = urlFrag.toString();
        System.out.println(url);

//        設定 QRcode 生成的容錯率等
        int width = 300;
        int height = 300;
        // 設定編碼格式與容錯率
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 開始產生 QRCode
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);


        //      測試用：存入硬碟
        // 設置QRCode的存放目錄、檔名與圖片格式
        String filePath = "C:/Users/Tibame_T14/Documents/ezTicket/images/QRcodeTest/";
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + ".jpg";
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        String format = "jpg";
        MatrixToImageWriter.writeToPath(matrix, format, path);
        System.out.println("path=" + path.toString());


        // 將 BitMatrix 轉換為 ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
        // 將 ByteArrayOutputStream 轉換為 byte[]
        byte[] bytes = baos.toByteArray();
        // 將 byte[] 轉換為 Base64 字串
        String base64String = Base64.getEncoder().encodeToString(bytes);
        System.out.println("轉換後的Base64：" + base64String);
        return base64String;
    }

    //    由 Redis 取出 QR code 圖片
    public String getQRcode(Integer collectno) {
        Optional<CollectRedis> optCr = collectRedisRepository.findById(collectno.toString());
        String img = null;
        if (optCr.isPresent()) {
            img = optCr.get().getQrcode();
            System.out.println("取得圖片");
        } else {
            Resource resource = resourceLoader.getResource("classpath:static/images/event-imgs/timeout.jpg");
            try (InputStream inputStream = resource.getInputStream()) {
                // 讀取圖片的內容
                byte[] imageBytes = inputStream.readAllBytes();
                // 將圖片的內容轉換為 Base64 字串
                img = Base64.getEncoder().encodeToString(imageBytes);
                System.out.println("預設圖片");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    //    分票功能
    @Transactional
    public boolean updateCollect(Integer collectno, String memail) {
//        Member mtest = memberRepository.findByMemail("member1@example.com");
//        System.out.println(mtest);
        Member member = memberRepository.findByMemail(memail);
        if (member == null || member.getMemberstatus() != 1) {
            System.out.println("查無會員/非啟用中會員");
            return false;
        }
        Integer memberno = member.getMemberno();
        Optional<Collect> optC = collectRepository.findById(collectno);
        if (optC.isEmpty()) {
            System.out.println("查無票券");
            return false;
        }
        Collect newC = optC.get();
        newC.setMemberno(memberno);
        collectRepository.save(newC);
        return true;
    }

}


