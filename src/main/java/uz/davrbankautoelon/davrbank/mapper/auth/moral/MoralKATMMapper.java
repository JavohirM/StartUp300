package uz.davrbankautoelon.davrbank.mapper.auth.moral;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralInfoDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralKATMDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MoralKATMMapper {

    public MoralKATMDto toKTM(MoralInfoDto dto, String branch, String phone, String amount, String id) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();

        if (
                dto.getSubject().getTin() == null || dto.getSubject().getTin().equals("") ||
                        dto.getSubject().getName() == null || dto.getSubject().getName().equals("") ||
                        dto.getSubject().getAddress_region() == null || dto.getSubject().getAddress_region().equals("") ||
                        dto.getSubject().getAddress_subregion() == null || dto.getSubject().getAddress_subregion().equals("") ||
                        dto.getSubject().getAddress_code() == null || dto.getSubject().getAddress_code().equals("") ||
                        dto.getSubject().getProperty_form() == null || dto.getSubject().getProperty_form().equals("") ||
                        dto.getSubject().getJuridical_form() == null || dto.getSubject().getJuridical_form().equals("") ||
                        dto.getSubject().getOked() == null || dto.getSubject().getOked().equals("") ||
                        dto.getSubject().getSubject_type() == null || dto.getSubject().getSubject_type().equals("") ||
                        dto.getSubject().getResidency_code() == null || dto.getSubject().getResidency_code().equals("")


        ) {
            return null;
        }


        MoralKATMDto ktmDto = new MoralKATMDto();


        /**
         * Set KATM header
         */
        MoralKATMDto.MoralKATMHeader header = new MoralKATMDto.MoralKATMHeader();
        header.setCode(branch);
        header.setType("B");

        /**
         * Set KATM body
         */
        MoralKATMDto.MoralKATMBody body = new MoralKATMDto.MoralKATMBody();
        body.setClaim_id(id);
        body.setClaim_date(dtf.format(now));
        body.setInn(dto.getSubject().getTin());
        body.setClaim_number("1");
        body.setAgreement_number("1");
        body.setAgreement_date(dtf.format(now));
        body.setResident(dto.getSubject().getResidency_code());
        body.setJuridical_status(dto.getSubject().getSubject_type());
        body.setNibbd("12345678");
        body.setClient_type("09");
        body.setName(dto.getSubject().getName());
        body.setLive_cadastr("000123456789123456789");
        body.setOwner_form(dto.getSubject().getProperty_form());
        body.setGoverment("2");
        body.setRegistration_region(dto.getSubject().getAddress_region());
        body.setRegistration_district(dto.getSubject().getAddress_subregion());
        body.setRegistration_address(dto.getSubject().getAddress_code());
        body.setPhone(phone);
        body.setHbranch("");
        body.setOked(dto.getSubject().getOked());
        body.setOkpo(dto.getSubject().getJuridical_form());
        body.setCredit_type("25");
        body.setSumma(amount);
        body.setProcent("30");
        body.setCredit_duration("36.00");
        body.setCredit_exemption("");
        body.setCurrency("000");

        /**
         * Set KATM dto
         */
        ktmDto.setHeader(header);

        ktmDto.setRequest(body);
        return ktmDto;
    }
}
