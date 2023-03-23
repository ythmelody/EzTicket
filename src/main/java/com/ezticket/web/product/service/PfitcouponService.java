package webapp.pfitcoupon.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.pfitcoupon.dto.PfitcouponDTO;
import webapp.pfitcoupon.pojo.Pfitcoupon;
import webapp.pfitcoupon.repository.PfitcouponRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PfitcouponService {

    @Autowired
    private PfitcouponRepository pfitcouponRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PfitcouponDTO EntityToDTO(Pfitcoupon pfitcoupon){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PfitcouponDTO pfitcouponDTO = new PfitcouponDTO();
        pfitcouponDTO = modelMapper.map(pfitcoupon, PfitcouponDTO.class);
        return pfitcouponDTO;
    }
    public List<PfitcouponDTO> getAllPfitcoupon(){
    modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);

    return pfitcouponRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
}
}
