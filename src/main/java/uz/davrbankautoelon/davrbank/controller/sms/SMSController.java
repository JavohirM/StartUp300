package uz.davrbankautoelon.davrbank.controller.sms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.sms.MainSMSDto;
import uz.davrbankautoelon.davrbank.service.auth.sms.MessageService;

@RestController
public class SMSController {

    private final MessageService service;

    public SMSController(MessageService service) {
        this.service = service;
    }

    @RequestMapping("/api/v1/sms")
    public Object callSMS(String phoneNumber, String text){
        MainSMSDto smsDto = new MainSMSDto();
        return service.callSMS(smsDto, phoneNumber, text);

    }
}
