package uz.davrbankautoelon.davrbank.service.auth.sms;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.davrbankautoelon.davrbank.dto.sms.ContextDto;
import uz.davrbankautoelon.davrbank.dto.sms.MainSMSDto;
import uz.davrbankautoelon.davrbank.dto.sms.MessagesDto;
import uz.davrbankautoelon.davrbank.dto.sms.SMSDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final static String URI = "http://91.204.239.44/broker-api/send";
    private final static String LOGIN = "davrbank3";
    private final static String PASSWORD = "B9pd5Da78K";


    public Object callSMS(MainSMSDto dto, String phoneNumber, String text) {

        ContextDto contextDto = new ContextDto();
        contextDto.setText(text);

        SMSDto smsDto = new SMSDto();
        smsDto.setContent(contextDto);
        smsDto.setOriginator("DavrBank");

        MessagesDto messagesDto = new MessagesDto();
        messagesDto.setMessageID("abc000000001");
        messagesDto.setRecipient(phoneNumber);
        messagesDto.setSms(smsDto);

        List<MessagesDto> messagesDtos = new ArrayList<>();
        messagesDtos.add(messagesDto);
        dto.setMessages(messagesDtos);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(LOGIN, PASSWORD);
        var request = new HttpEntity<>(dto, headers);
        try {
            return restTemplate.postForObject(URI, request, String.class);
        }
        catch (HttpClientErrorException ex){

            HttpClientErrorException exception = restTemplate.postForObject(URI, request, ex.getClass());

            System.out.println("asdas");
            return null;


        }
    }

}

