package uz.davrbankautoelon.davrbank.dto.auth.moral;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoralKATMDto implements Serializable {
    private MoralKATMHeader header;
    private MoralKATMBody request;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoralKATMHeader{
        private String type;
        private String code;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoralKATMBody{
        private String claim_id;
        private String claim_date;
        private String inn;
        private String claim_number;
        private String agreement_number;
        private String agreement_date;
        private String resident;
        private String juridical_status;
        private String nibbd;
        private String client_type;
        private String name;
        private String live_cadastr;
        private String owner_form;
        private String goverment;
        private String registration_region;
        private String registration_district;
        private String registration_address;
        private String phone;
        private String hbranch;
        private String oked;
        private String katm_sir;
        private String okpo;
        private String credit_type;
        private String summa;
        private String procent;
        private String credit_duration;
        private String credit_exemption;
        private String currency;
    }


}
