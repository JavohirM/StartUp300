package uz.davrbankautoelon.davrbank.controller.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.controller.sms.SMSController;
import uz.davrbankautoelon.davrbank.dto.auth.RequestValidationDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectionDto;
import uz.davrbankautoelon.davrbank.enums.SMSEnums;
import uz.davrbankautoelon.davrbank.mapper.auth.RejectMapper;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.request.ValidationService;

@RestController
@Slf4j
public class ValidateController {

    private final ValidationService service;
    private final SMSController controller;
    private final RejectMapper mapper;

    public ValidateController(ValidationService service, SMSController controller, RejectMapper mapper) {
        this.service = service;
        this.controller = controller;
        this.mapper = mapper;
    }

    @RequestMapping(value = "api/v1/request/status", method = RequestMethod.POST)
    public ResponseEntity<DataDto<String>> validate(@RequestBody RequestValidationDto dto) {
        RejectionDto reject = mapper.reject(dto.getId(), dto.getBranch());
        String result = "";
        if(dto.getClientType().equals("08")){
            service.validatePhysical(dto.getId(), dto.getStatus(), dto.getRejectMessage(), reject);
            result = "status physical is changed to " + dto.getStatus();
            log.info(result);
        }
        if(dto.getClientType().equals("09")){
            service.validateMoral(dto.getId(), dto.getStatus(), dto.getRejectMessage(), reject);
            result = "status moral is changed to " + dto.getStatus();
            log.info(result);
        }
        if(dto.getStatus().equals("A")) {
            controller.callSMS(dto.getPhone(), SMSEnums.REJECT.getText());
        }
        else {
            controller.callSMS(dto.getPhone(), SMSEnums.RESPONSE.getText());
        }
        return new ResponseEntity<>(new DataDto<>(result, true), HttpStatus.OK);
    }
}
