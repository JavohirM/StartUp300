package uz.davrbankautoelon.davrbank.mapper.auth.moral;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralImagesDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._MoralSecondImage;

@Component
public class SecondaryImageMapper implements MapStructMapper<MoralImagesDto.SecondaryImage, _MoralSecondImage> {
    @Override
    public _MoralSecondImage fromDto(MoralImagesDto.SecondaryImage dto) {
        _MoralSecondImage image = new _MoralSecondImage();
        image.setImagePath(dto.getImagePath());
        return image;
    }
}
