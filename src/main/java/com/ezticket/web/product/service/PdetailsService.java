package webapp.pdetails.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.pdetails.dto.PdetailsDTO;
import webapp.pdetails.pojo.Pdetails;
import webapp.pdetails.repository.PdetailsRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PdetailsService {
    @Autowired
    private PdetailsRepository pdetailsRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PdetailsDTO EntityToDTO(Pdetails pdetails){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PdetailsDTO pdetailsDTO = new PdetailsDTO();
        pdetailsDTO = modelMapper.map(pdetails, PdetailsDTO.class);

        return pdetailsDTO;
    }
    public List<PdetailsDTO> getAllPdetails(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return pdetailsRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
