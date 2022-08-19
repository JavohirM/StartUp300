package uz.davrbankautoelon.davrbank.mapper.auth.moral;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._Moral;
import uz.davrbankautoelon.davrbank.model.auth._MoralImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoralMapper implements MapStructMapper<MoralDto, _Moral> {
    private final MoralImgMapper mapper;

    public MoralMapper(MoralImgMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public _Moral fromDto(MoralDto dto) {

        List<_MoralImage> collect = new ArrayList<>();
        dto.getImages().forEach( p -> {
            collect.add(mapper.fromDto(p));
        });
        _Moral moral = new _Moral();
        moral.setInn(dto.getInn());
        moral.setName(dto.getName());
        moral.setClient_type(dto.getClient_type());
        moral.setAmount(dto.getAmount());
        moral.setRequestId(dto.getRequestId());
        moral.setStatus(dto.getStatus());
        moral.setBranch(dto.getBranch());
        moral.setPhone(dto.getPhone());
        moral.setSubBranch(dto.getSubBranch());
        moral.setImages(collect);
        return moral;
    }
}
