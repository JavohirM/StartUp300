package uz.davrbankautoelon.davrbank.service.auth.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralInfoDto;

@Component
@Slf4j
public class MoralClientInfo {

    private final RestTemplate restTemplate;
    @Value("${URI_MORAL_CLIENT_INFO}")
    private String URI;

    public MoralClientInfo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MoralInfoDto getInfo(String branch, String inn) {
        log.info("Call NIBBD Moral");
        String url = URI + "?branch=" + branch + "&tin=" + inn;
        MoralInfoDto forObject = restTemplate.getForObject(url, MoralInfoDto.class);
        log.info(url);
        assert forObject != null;
        log.info("Get response: " + forObject.getSubject().toString());
        return  forObject;
    }
}
