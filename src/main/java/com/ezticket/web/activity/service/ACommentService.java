package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.ACommentDto;
import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.repository.ACommentDao;
import com.ezticket.web.activity.repository.ACommentRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ACommentService {
    @Autowired
    private ACommentRepository aCommentRepository;
    @Autowired
    private ACommentDao aCommentDao;
    @Autowired
    private ModelMapper modelMapper;

    public List<ACommentDto> getAllAComments(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aCommentRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public int updateAComment(int commentId, int commentStatus){
        return aCommentRepository.update(commentId, commentStatus);
    }

    public Optional<AComment> getACommentForUpdate(Integer commentId){
        return aCommentRepository.findById(commentId);
    }

    public List<ACommentDto> getACommentsByActName(String actName){
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aCommentRepository.getACommentByActName(actName)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public List getACommentAnames(){
        return  aCommentRepository.getACommentANames();
    }

    public List<AComment> getACommentsBySelection(Map map) {
        return  aCommentDao.getByCompositeQuery(map);
    };

    public List<ACommentDto> getACommentByActNo(Integer actNo){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aCommentRepository.getACommentByActivityNo(actNo)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());}

    public boolean insertAComment(AComment aComment){
        aComment.setACommentDate(new Date(System.currentTimeMillis()));
        aComment.setACommentStatus(0);
        aComment.setALike(0);
        System.out.println(aComment);
        aCommentRepository.save(aComment);
        return true;
    }

    private ACommentDto EntityToDTO(AComment aComment) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        ACommentDto aCommentDto = new ACommentDto();
        aCommentDto = modelMapper.map(aComment, ACommentDto.class);
        return aCommentDto;
    }
}
