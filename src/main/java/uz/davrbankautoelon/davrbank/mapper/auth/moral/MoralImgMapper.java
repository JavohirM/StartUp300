package uz.davrbankautoelon.davrbank.mapper.auth.moral;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralImagesDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._MoralImage;
import uz.davrbankautoelon.davrbank.model.auth._MoralSecondImage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoralImgMapper implements MapStructMapper<MoralImagesDto, _MoralImage> {
    private final SecondaryImageMapper mapper;

    public MoralImgMapper(SecondaryImageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public _MoralImage fromDto(MoralImagesDto dto) {
        List<_MoralSecondImage> collect = dto.getDocument1().stream().map(mapper::fromDto).collect(Collectors.toList());
        _MoralImage image = new _MoralImage();
        image.setCertificate(dto.getCertificate());
        image.setPassport(dto.getPassport());
        image.setDocument1(collect);
        image.setDocument2(dto.getDocument2());
        image.setFile(dto.getFile());
        return image;
    }
}
