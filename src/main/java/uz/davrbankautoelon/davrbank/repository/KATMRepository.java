package uz.davrbankautoelon.davrbank.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralKATMDto;
import uz.davrbankautoelon.davrbank.dto.katm.RegistrationRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RequestMapperDto;
import uz.davrbankautoelon.davrbank.mapper.auth.RegistrationRequestMapper;
import uz.davrbankautoelon.davrbank.model.auth._Request;
import uz.davrbankautoelon.davrbank.response.DataDto;

@Repository
@Slf4j
public class KATMRepository {

    private final IRegistrationRequest request;
    private final RegistrationRequestMapper mapper;

    private final JdbcTemplate jdbcTemplate;

    public KATMRepository(IRegistrationRequest request, RegistrationRequestMapper mapper, JdbcTemplate jdbcTemplate) {
        this.request = request;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }


    public ResponseEntity<DataDto<String>> send(RegistrationRequestDto dto, String requestId) {
        RequestMapperDto dto1 = new RequestMapperDto();
        dto1.setRequest(dto.getRequest().toString());
        dto.setHeader(dto.getHeader());
        dto1.setRequestId(requestId);
        _Request request = mapper.fromDto(dto1);
        try {
            log.info("Call registration registration_request table");
            log.info(request.toString());
            _Request save = this.request.save(request);
            log.info("Successfully created registration request record");
            log.info(save.toString());
            return new ResponseEntity<>(new DataDto<>("Saved successfully", true), HttpStatus.CREATED);
        } catch (Exception ex) {
            log.warn("Error in creation registration_request table");
            return new ResponseEntity<>(new DataDto<>("Failed", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String saveKATMRequest(MoralKATMDto dto, String requestId){
        RequestMapperDto dto1 = new RequestMapperDto();
        dto1.setRequest(dto.getRequest().toString());
        dto.setHeader(dto.getHeader());
        dto1.setRequestId(requestId);
        _Request result =mapper.fromDto(dto1);
        try {
            log.info("Call registration registration_request table");
            log.info(request.toString());
            _Request save = this.request.save(result);
            log.info("Successfully created registration request record");
            log.info(save.toString());
            return "Success";
        } catch (Exception ex) {
            log.warn("Error in creation registration_request table");
            return "Erorr";
        }
    }


    public Object update(String details, String id) {
        try {

            String sql = "update startup300.registration_request set response='" + details + "' where request_id='" + id + "'";
            log.info("update request in table column response " + sql);
            jdbcTemplate.execute(sql);
            return "Success";
        } catch (Exception ex) {
            log.info("Error on update " + ex.getMessage());
            return null;
        }
    }

    public Object reject(String details, String id) {
        try {
            String requestID = "select request_id from auto_elon_physical where id='" + id + "'";
            log.info(requestID);
            String s = jdbcTemplate.queryForObject(requestID, String.class);
            log.info(s);
            String sql = "update registration_request set reject='" + details + "' where request_id='" + s + "'";
            log.info("update request in table column reject  " + sql);
            jdbcTemplate.execute(sql);
            return "Success";
        } catch (Exception ex) {
            log.info("Error on update " + ex.getMessage());
            return null;
        }
    }

    public String getRequestID(String id) {
        try {
            String requestID = "select request_id from auto_elon_physical where id='" + id + "'";
            log.info(requestID);
            String s = jdbcTemplate.queryForObject(requestID, String.class);
            log.info(s);
            return s;
        } catch (Exception ex) {
            log.info("Error on update " + ex.getMessage());
            return null;
        }
    }

}
