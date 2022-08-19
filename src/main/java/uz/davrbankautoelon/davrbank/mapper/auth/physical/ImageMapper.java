package uz.davrbankautoelon.davrbank.mapper.auth.physical;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._Image;

@Component
public class ImageMapper implements MapStructMapper<String, _Image> {



    @Override
    public _Image fromDto(String dto) {
        _Image image = new _Image();
        image.setPathToImage(dto);
        return image;
    }
}
