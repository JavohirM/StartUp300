package uz.davrbankautoelon.davrbank.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoDto implements Serializable {
    private String subject_state;
    private String inn;
    private String inn_registrated;
    private String inn_registrated_gni;
    private String pin;
    private String surname;
    private String givenname;
    private String patronym;
    private String birth_date;
    private String sex;
    private String passport_seria;
    private String passport_number;
    private String date_issue;
    private String date_expiry;
    private String give_place;
    private String give_place_name;
    private String birth_country;
    private String birth_country_name;
    private String birth_place;
    private String nationality;
    private String nationality_desc;
    private String citizenship;
    private String citizenship_name;
    private String domicile_kadastr;
    private String domicile_country;
    private String domicile_region;
    private String domicile_district;
    private String domicile_place_desc;
    private String domicile_street_desc;
    private String domicile_address;
}
