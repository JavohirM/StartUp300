package uz.davrbankautoelon.davrbank.service.auth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.davrbankautoelon.davrbank.dto.client.ClientInfoDto;
import uz.davrbankautoelon.davrbank.dto.client.GetInfoDto;

@Service
@Slf4j
public class ClientInfoService {

    @Value("${URI_GET_CLIENT_INFO}")
    private String URI;

    private final RestTemplate restTemplate;

    public ClientInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ClientInfoDto getInfo(GetInfoDto dto){

        log.info("Call NIBBD get client info");
        ResponseEntity<ClientInfoDto> forEntity = restTemplate.getForEntity(URI+dto.getSerial()+"/"+dto.getNumber()+"?birthDate="+dto.getDate(), ClientInfoDto.class);
        log.info("Response" + forEntity.getBody());
        return forEntity.getBody();
    }
}
