package uz.davrbankautoelon.davrbank.mapper.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.katm.RejectData;
import uz.davrbankautoelon.davrbank.dto.katm.RejectSecurity;
import uz.davrbankautoelon.davrbank.dto.katm.RejectionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class RejectMapper {

    @Value("${LOGIN_KATM}")
    private String login;
    @Value("${PASSWORD_kATM}")
    private String password;

    public RejectionDto reject(String requestID, String branch){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        RejectionDto dto = new RejectionDto();
        RejectData data = new RejectData();
        RejectSecurity security = new RejectSecurity();
        security.setPLogin(login);
        security.setPPassword(password);
        data.setPHead("051");
        data.setPCode(branch);
        data.setPClaimId(requestID);
        data.setPDeclineDate(dtf.format(now));
        data.setPDeclineNumber("1");
        data.setPDeclineReason("9");
        data.setPDeclineReasonNote("1");

        dto.setSecurity(security);
        dto.setData(data);
        log.info(dto.toString());
        return dto;
    }
}
