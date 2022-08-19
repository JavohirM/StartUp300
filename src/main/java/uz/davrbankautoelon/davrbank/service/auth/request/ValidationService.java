package uz.davrbankautoelon.davrbank.service.auth.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.dto.katm.RejectRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectionDto;
import uz.davrbankautoelon.davrbank.service.auth.request.physical.SendRequestToKATMService;

@Service
@Slf4j
public class ValidationService {

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;

    private final SendRequestToKATMService ktmService;

    public ValidationService(JdbcTemplate jdbcTemplate, SendRequestToKATMService ktmService) {
        this.jdbcTemplate = jdbcTemplate;
        this.ktmService = ktmService;
    }

    public void validateMoral(String id, String name, String rejectMessage, RejectionDto dto) {
        try {
            Object reject = ktmService.rejectRequest(dto);
            String moral = "update " + schema + "._moral set request_id = '" + id + "'";
            String status = ", status= '" + name + "'";
            String rejection = ", reject_message = '"+rejectMessage+"'";
            String where = " where request_id = '" + id + "'";
            if (!rejectMessage.equals("")) {
                moral += status + rejection + where;
            }else {
                moral += status + where;
            }
            log.info(moral);
            String rejectResponse = "update " + schema + ".registration_request set reject = '" + reject.toString() + "' where request_id = '" + id + "'";
            log.info(rejectResponse);
            jdbcTemplate.execute(moral);
            jdbcTemplate.execute(rejectResponse);
        } catch (Exception e) {
            log.info("Error in validation: {}", e.getMessage());
        }
    }

    public void validatePhysical(String id, String name, String rejectMessage, RejectionDto dto) {
        try {
            Object reject = ktmService.rejectRequest(dto);
            String sql = "update " + schema + ".auto_elon_physical set request_id = '" + id + "'";
            String status = ", status= '" + name + "'";
            String rejection = ", reject_message = '"+rejectMessage+"'";
            String where = " where request_id = '" + id + "'";
            if (!rejectMessage.equals("")) {
                sql += status + rejection + where;
            }else {
                sql += status + where;
            }
            log.info(sql);
            String rejectResponse = "update " + schema + ".registration_request set reject = '" + reject.toString() + "' where request_id = '" + id + "'";
            log.info(rejectResponse);
            jdbcTemplate.execute(sql);
            jdbcTemplate.execute(rejectResponse);
        } catch (Exception e) {
            log.info("Error in validation: {}", e.getMessage());
        }
    }
}
