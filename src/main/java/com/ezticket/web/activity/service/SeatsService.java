package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.BlockModelVO;
import com.ezticket.web.activity.pojo.BlockPrice;
import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.pojo.SeatsModelVO;
import com.ezticket.web.activity.repository.SeatsModelDAO;
import com.ezticket.web.activity.repository.SeatsRedisDAO;
import com.ezticket.web.activity.repository.SeatsRepository;
import com.ezticket.web.activity.repository.impl.BlockModelDAOImpl;
import com.ezticket.web.activity.repository.impl.SeatsModelDAOImpl;
import com.ezticket.web.activity.repository.impl.SeatsRedisDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class SeatsService {

    private SeatsModelDAOImpl seatsModelDAOImpl = new SeatsModelDAOImpl();
    private BlockModelDAOImpl blockModelDAOImpl = new BlockModelDAOImpl();

    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private SeatsRedisDAO seatsRedisDAO;

    @Autowired
    private BlockPriceService blockPriceService;

    public List<Seats> getSeatsBySessionAndBlockNo(Integer sessionNo, Integer blockNo) {
        return seatsRepository.findBySessionNoAndBlockNo(sessionNo, blockNo);
    }

    public List<Integer> getToSellSeatsByBlock(int blockNo, int seatStatus) {
        return seatsRepository.getIdsByBlockNoAndSeatStatus(blockNo, seatStatus);
    }

    public int updateOneSeat(String blockName, String realX, String realY, int seatStatus, int seatNo) {
        return seatsRepository.update(blockName, realX, realY, seatStatus, seatNo);
    }

    public boolean insertNewSeat(Seats seats) {
        seatsRepository.save(seats);
        return true;
    }

    public int updateOneSeatStatus(int seatStatus, int seatNo) {
        return seatsRepository.updateStatus(seatStatus, seatNo);
    }

    public List<Integer> getActBlockHasSeats(int actNo) {
        return seatsRepository.getActBlockHasSeats(actNo);
    }

    public boolean isSessionBlockSeatsExist(int sessionNo, int blockNo){
        if (!seatsRepository.getSessionBlockSeatsExist(sessionNo, blockNo).isEmpty()){
            return true;
        }
        return false;
    }

    public int setSessionSeats(int seatStatus, int seatNo, int sessionNo) {

        if (seatStatus == -1) { // 當座位變為限制座位時，將座位編號移出 Redis 中
            seatsRedisDAO.setDelKV("Session" + sessionNo, String.valueOf(seatNo));
            return 1;
        }

        if (seatStatus == 0) { // 當座位變為保留席時，將座位編號移出 Redis 中
            seatsRedisDAO.setDelKV("Session" + sessionNo, String.valueOf(seatNo));
            return 1;
        }

        if (seatStatus == 1) { // 當座位取消選取或超過結帳時間時，將座位編號移出 Redis 中
            seatsRedisDAO.setDelKV("Session" + sessionNo, String.valueOf(seatNo));
            return 1;
        }

        if (seatStatus == 2) { // 當座位結帳成功時，將座位編號移出 Redis 並修改 MySQL 座位狀態
            seatsRedisDAO.setDelKV("Session" + sessionNo, String.valueOf(seatNo));
            updateOneSeatStatus(seatStatus, seatNo);
            return 1;
        }

        if (seatStatus == 3) { // 當座位被選取時，將座位編號存入 Redis 中
            seatsRedisDAO.setAddKV("Session" + sessionNo, String.valueOf(seatNo));
            return 1;
        }

        return 0;
    }

    public List<Integer> getSeatsBySystem(int ticketQTY, int blockNo, int sessionNo) {
        List<Integer> toSellSeats = getToSellSeatsByBlock(blockNo, 1);

        Set<String> lockedSeats = getLockedSeatsBySession(sessionNo);

        for (String seat : lockedSeats) {
            int seatNo = Integer.parseInt(seat);

            if (toSellSeats.contains(seatNo)) {
                toSellSeats.remove(toSellSeats.indexOf(seatNo));
            }
        }

        List<Integer> returnedList = new LinkedList<Integer>();

        for (int i = 0; i < ticketQTY; i++) {
            returnedList.add(toSellSeats.get(i));
        }

        return returnedList;
    }

    public Set<String> getLockedSeatsBySession(int sessionNo) {
        return seatsRedisDAO.setFindAllValues("Session" + sessionNo);
    }

    public boolean getSeasBySession(int sessionNo) {
        List<Seats> list = seatsRepository.findBySessionNo(sessionNo);
        if (!list.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<Integer> getSeatsNumbers(int sessionNo, int blockNo) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(seatsRepository.getToSellNumber(sessionNo, blockNo));
        list.add(seatsRepository.getSoldNumber(sessionNo, blockNo));
        return list;
    }


    public boolean copySessionSeats(int copiedSessionNo, int sessionNo, int activityNo) {
        // 直接取得場次座位表，並修改 sessionNo 後回傳到資料庫
        List<Seats> sessionSeats = seatsRepository.findBySessionNo(copiedSessionNo);

        for (Seats seat : sessionSeats) {

            Seats newSeat = new Seats();
            newSeat.setSessionNo(sessionNo);
            newSeat.setBlockNo(seat.getBlockNo());
            newSeat.setBlockName(seat.getBlockName());
            newSeat.setX(seat.getX());
            newSeat.setY(seat.getY());
            newSeat.setRealX(seat.getRealX());
            newSeat.setRealY(seat.getRealY());

            if(seat.getSeatStatus() != (-1))
                newSeat.setSeatStatus(1);

            seatsRepository.save(newSeat);
        }

        return true;
    }

    public boolean copyModelSeats(int modelNo, int sessionNo, int activityNo) {
        // 取得所有模板區域
        List<BlockModelVO> modelBlocks = blockModelDAOImpl.getAll();

        // 取得所有模板的座位
        List<SeatsModelVO> modelSeats = seatsModelDAOImpl.getAll();

        // 排除非所需模板的區域
        List<BlockModelVO> targetmodelBlocks = new ArrayList<BlockModelVO>();

        for (BlockModelVO modelblock : modelBlocks) {
            if (modelblock.getModelno() == modelNo) {
                targetmodelBlocks.add(modelblock);
            }
        }

        List<Integer> blockArr = new ArrayList<Integer>();
        for (int i = 0; i < targetmodelBlocks.size(); i++) {
            blockArr.add(targetmodelBlocks.get(i).getBlockno());
        }

        // 排除非所需模板區域的座位
        List<SeatsModelVO> targetModelSeats = new ArrayList<SeatsModelVO>();

        for (SeatsModelVO modelSeat : modelSeats) {
            if (blockArr.contains(modelSeat.getBlockno())) {
                targetModelSeats.add(modelSeat);
            }
        }

        // 取得節目區域票價詳情
        List<BlockPrice> actBlocks = blockPriceService.getBlockPriceByActivityNo(activityNo);

        // 只要模板的區塊與節目的區塊數量相等就可以直接複製
        int totalModelBlocks = targetmodelBlocks.size();
        int totalActBlocks = actBlocks.size();

        if (totalModelBlocks != totalActBlocks) {
            return false;
        }

        // 確認兩個區塊編號的差異並修改模板中的區塊編號(從 1 開始)
        int firstModelBlockNo = blockArr.get(0);
        int firstActBlockNo = actBlocks.get(0).getBlockNo();
        int countDiff = firstActBlockNo - firstModelBlockNo;

        // 新增一個 ArrayList 預備回傳到資料庫
        List<Seats> returendList = new ArrayList<Seats>();

        for (SeatsModelVO modelSeat : targetModelSeats) {

            int blockNo = modelSeat.getBlockno() + countDiff;
            String blockName = actBlocks.get(blockNo - (countDiff + 1)).getBlockName();

            Seats seat = new Seats();
            seat.setSessionNo(sessionNo);
            seat.setBlockNo(blockNo);
            seat.setBlockName(blockName);
            seat.setX(modelSeat.getX());
            seat.setY(modelSeat.getY());
            seat.setRealX(modelSeat.getRealx());
            seat.setRealY(modelSeat.getRealy());
            seat.setSeatStatus(modelSeat.getSeatStatus());

            returendList.add(seat);
        }

        // 將異動完成的座位存起來
        for (Seats seat : returendList) {
            seatsRepository.save(seat);
        }

        return true;
    }

    public boolean deleteSeats(Integer sessionNo, Integer blockNo){
        if(seatsRepository.deleteBySessionNoAndBlockNo(sessionNo, blockNo) > 0){
            return true;
        }
        return false;
    }


}
