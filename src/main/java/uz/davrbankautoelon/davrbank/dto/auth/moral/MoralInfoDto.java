package uz.davrbankautoelon.davrbank.dto.auth.moral;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoralInfoDto implements Serializable {
    private SubjectMoral subject;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class SubjectMoral{
        private String tin;
        private String citizenship_code;
        private String name;
        private String name_short;
        private String address_region;
        private String address_subregion;
        private String address_code;
        private String property_form;
        private String legal_form;
        private String juridical_form;
        private String oked;
        private String header_tin;
        private String registration_issuer;
        private String registration_doc_number;
        private String registration_date;
        private String issuer_region;
        private String issuer_subregion;
        private String registration_expire_date;
        private String subject_type;
        private String residency_code;
    }
}
