package uz.davrbankautoelon.davrbank.mapper.auth.physical;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalDto;
import uz.davrbankautoelon.davrbank.dto.auth.UploadFileResponse;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.mapper.auth.physical.ImageMapper;
import uz.davrbankautoelon.davrbank.model.auth._Physical;


/**
 * Status of request
 * N - not valid
 * v - valid
 * A - rejected
 */


@Component
public class PhysicalMapper implements MapStructMapper<PhysicalDto, _Physical> {

    private final ImageController controller;

    public PhysicalMapper(ImageController controller) {
        this.controller = controller;
    }


    @Override
    public _Physical fromDto(PhysicalDto dto) {
        UploadFileResponse uploadFileResponse = controller.uploadFile(dto.getImage_path());
        _Physical physical = new _Physical();
        physical.setSerialNumber(dto.getSerialNumber());
        physical.setFirstName(dto.getFirst_name());
        physical.setLastName(dto.getLast_name());
        physical.setPatronymic(dto.getPatronymic());
        physical.setClient_type(dto.getClient_type());
        physical.setPassportNumber(dto.getPassportNumber());
        physical.setPhoneNumber(dto.getPhoneNumber());
        physical.setSumma(dto.getSumma());
        physical.setSubBranch(dto.getSubBranch());
        physical.setBirthDate(dto.getBirthDate());
        physical.setBranch(dto.getBranch());
        physical.setRequestId(dto.getRequestId());
        physical.setImage_path(uploadFileResponse.getFileName());
        physical.setStatus(dto.getStatus());
        return physical;
    }
}
