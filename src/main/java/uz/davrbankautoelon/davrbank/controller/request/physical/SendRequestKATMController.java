package uz.davrbankautoelon.davrbank.controller.request.physical;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.katm.RegistrationRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectRequestDto;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.request.physical.SendRequestToKATMService;

@RestController
public class SendRequestKATMController {



    private final SendRequestToKATMService service;

    public SendRequestKATMController(SendRequestToKATMService service) {
        this.service = service;
    }

    @RequestMapping(value = "api/v1/katm", method = RequestMethod.POST)
    public ResponseEntity<DataDto<String>> sendRequest(RegistrationRequestDto dto, String requestId){
        return service.send(dto, requestId);
    }
    @RequestMapping(value = "api/v1/katm/post", method = RequestMethod.POST)
    public Object post(@RequestBody RegistrationRequestDto dto){
        return service.post(dto);
    }

    public Object update(String details, String requestId){
        return service.update(details,requestId);
    }

    public Object reject(RejectRequestDto dto){
        return service.reject(dto);
    }
    public Object rejectUpdate(String details, String requestId){
        return service.rejectUpdate(details,requestId);
    }
    public String getRequestID(String id){
        return service.getRequestID(id);
    }
}
