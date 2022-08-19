package uz.davrbankautoelon.davrbank.service.auth.request.physical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.davrbankautoelon.davrbank.dto.auth.ErrorDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralKATMDto;
import uz.davrbankautoelon.davrbank.dto.katm.RegistrationRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectionDto;
import uz.davrbankautoelon.davrbank.repository.KATMRepository;
import uz.davrbankautoelon.davrbank.response.DataDto;

@Service
@Slf4j
public class SendRequestToKATMService {

    private final KATMRepository repository;
    private final RestTemplate restTemplate;
    @Value("${URI_SEND_REQUEST_PHYSICAL}")
    private String URI_PHYSICAL;
    @Value("${URI_SEND_REQUEST_MORAL}")
    private String URI_MORAL;
    @Value("${URI_REJECT_REQUEST}")
    private String REJECT_URI;
    @Value("${URI_REJECT}")
    private String REJECT;

    public SendRequestToKATMService(KATMRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<DataDto<String>> send(RegistrationRequestDto dto, String requestId) {
        log.info("Save request to table KATM");
        return repository.send(dto, requestId);
    }


    public Object post(RegistrationRequestDto dto) {
        try {
            log.info("Call KATM request url");
            HttpEntity<RegistrationRequestDto> entity = new HttpEntity<>(dto);

            log.info("Get Response from KATM ");
            return restTemplate.exchange(URI_PHYSICAL, HttpMethod.POST, entity, Object.class).getBody();

        } catch (HttpClientErrorException ex) {
            log.info("Error in Sending request to KATM");
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            return new ResponseEntity<>(new DataDto<>(errorDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Object postMoral(MoralKATMDto dto) {
        try {
            log.info("Call KATM request url");
            HttpEntity<MoralKATMDto> entity = new HttpEntity<>(dto);
            log.info("Get Response from KATM ");
            return restTemplate.exchange(URI_MORAL, HttpMethod.POST, entity, Object.class).getBody();

        } catch (HttpClientErrorException ex) {
            log.info("Error in Sending request to KATM");
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            return new ResponseEntity<>(new DataDto<>(errorDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Object rejectRequest(RejectionDto dto) {
        try {
            log.info("Call KATM request url : request {}", dto.toString());
            HttpEntity<?> entity = new HttpEntity<>(dto);
            log.info("Get Response from KATM : {}", entity.getBody());
            return restTemplate.exchange(REJECT, HttpMethod.POST, entity, Object.class).getBody();

        } catch (HttpClientErrorException ex) {
            log.info("Error in Sending request to KATM");
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            return new ResponseEntity<>(new DataDto<>(errorDto.toString(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Object update(String details, String requestId) {
        return repository.update(details, requestId);
    }


    public Object reject(RejectRequestDto dto) {
        try {
            log.info("Call KATM reject url");
            HttpEntity<RejectRequestDto> entity = new HttpEntity<>(dto);
            log.info("Get Response for rejection");
            Object body = restTemplate.exchange(REJECT_URI, HttpMethod.POST, entity, Object.class).getBody();
            log.error(body.toString());
            return body;
        } catch (Exception ex) {
            log.info("Error in Sending request to KATM " + ex.getMessage());
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            return new ResponseEntity<>(new DataDto<>(errorDto.toString(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Object rejectUpdate(String details, String requestId) {
        return repository.reject(details, requestId);
    }

    public String getRequestID(String id) {
        return repository.getRequestID(id);
    }
}