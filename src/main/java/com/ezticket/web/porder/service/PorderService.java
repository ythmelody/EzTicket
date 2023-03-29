package webapp.porder.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.porder.dto.PorderDTO;
import webapp.porder.dto.PorderDetailsDTO;
import webapp.porder.pojo.Porder;
import webapp.porder.repository.PorderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PorderService {

    @Autowired
    private PorderRepository porderRepository;
    @Autowired
    private ModelMapper modelMapper;

    // GetPordersByID
    public List<PorderDTO> getPordersByID(Integer id){
        return porderRepository.findByID(id)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public PorderDTO getPorderByID(Integer id) {
        Porder porder = porderRepository.getReferenceById(id);
        return EntityToDTO(porder);
    }
    // GetPorderDetailsByID
    public PorderDetailsDTO getPorderDetailsByID(Integer id) {
        Porder porder = porderRepository.findByPorderno(id);
        return EntityToDetailDTO(porder);
    }
    public PorderDetailsDTO EntityToDetailDTO(Porder porder){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PorderDetailsDTO porderDetailsDTO = modelMapper.map(porder, PorderDetailsDTO.class);
        porderDetailsDTO.setProducts(porder.getProducts());
        return porderDetailsDTO;
    }

    // UpdatePorderByID
    public PorderDTO updateByID(Integer id, Integer processStatus) {
        Porder porder = porderRepository.getReferenceById(id);
        porder.setPprocessstatus(processStatus);
        Porder updatedPorder = porderRepository.save(porder);
        return EntityToDTO(updatedPorder);
    }

    public PorderDTO EntityToDTO(Porder porder){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PorderDTO porderDTO = new PorderDTO();
        porderDTO = modelMapper.map(porder, PorderDTO.class);
        return porderDTO;
    }
    public List<PorderDTO> getAllPorder(){
    modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);

    return porderRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
    }

}
