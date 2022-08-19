package uz.davrbankautoelon.davrbank.service.auth.mib_katm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.davrbankautoelon.davrbank.dto.auth.ErrorDto;
import uz.davrbankautoelon.davrbank.dto.katm.KATMBodyDto;
import uz.davrbankautoelon.davrbank.dto.mib.MiBLoginDto;
import uz.davrbankautoelon.davrbank.dto.mib.MiBLoginResponseDto;
import uz.davrbankautoelon.davrbank.dto.mib.MiBSearchDto;
import uz.davrbankautoelon.davrbank.response.DataDto;


@Service
public class MiBKATMService {
    @Value("${USERNAME_MIB_KATM}")
    private String USERNAME;
    @Value("${PASSWORD_MIB_KATM}")
    private String PASSWORD;
    @Value("${URI_MIB_KATM}")
    private String URI;

    public MiBLoginResponseDto login(){
        MiBLoginDto dto = new MiBLoginDto();
        dto.setUsername(USERNAME);
        dto.setPassword(PASSWORD);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(dto, headers);
        try {
            return restTemplate.postForObject(URI, request, MiBLoginResponseDto.class);
        }
        catch (HttpClientErrorException ex){
           return null;
        }
    }

    public <C> ResponseEntity<DataDto<Object>> getDetails(C detail, String url){
        MiBLoginResponseDto login = login();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(login.getToken());
        var request = new HttpEntity<>(detail, headers);
        try {
            Object o = restTemplate.postForObject(url, request, Object.class);
            return new ResponseEntity<>(new DataDto<>(o, true), HttpStatus.OK);
        }
        catch (HttpClientErrorException ex){
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            return new ResponseEntity<>(new DataDto<>(errorDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


