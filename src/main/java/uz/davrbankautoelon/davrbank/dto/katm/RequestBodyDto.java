package uz.davrbankautoelon.davrbank.dto.katm;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RequestBodyDto implements Serializable {
    private String claim_id;
    private String claim_date;
    private String inn;
    private String claim_number;
    private String agreement_number;
    private String agreement_date;
    private String resident;
    private String document_type;
    private String document_serial;
    private String document_number;
    private String document_date;
    private String gender;
    private String client_type;
    private String birth_date;
    private String document_region;
    private String document_district;
    private String nibbd;
    private String family_name;
    private String name;
    private String patronymic;
    private String registration_region;
    private String registration_district;
    private String registration_address;
    private String phone;
    private String pin;
    private String katm_sir;
    private String credit_type;
    private String summa;
    private String procent;
    private String credit_duration;
    private String credit_exemtion;
    private String currency;
    private String live_address;
    private String live_cadastr;
    private String registration_cadastr;

    @Override
    public String toString() {
        return "RequestBodyDto{" +
                "claim_id='" + claim_id + '\'' +
                ", claim_date='" + claim_date + '\'' +
                ", inn='" + inn + '\'' +
                ", claim_number='" + claim_number + '\'' +
                ", agreement_number='" + agreement_number + '\'' +
                ", agreement_date='" + agreement_date + '\'' +
                ", resident='" + resident + '\'' +
                ", document_type='" + document_type + '\'' +
                ", document_serial='" + document_serial + '\'' +
                ", document_number='" + document_number + '\'' +
                ", document_date='" + document_date + '\'' +
                ", gender='" + gender + '\'' +
                ", client_type='" + client_type + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", document_region='" + document_region + '\'' +
                ", document_district='" + document_district + '\'' +
                ", nibbd='" + nibbd + '\'' +
                ", family_name='" + family_name + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", registration_region='" + registration_region + '\'' +
                ", registration_district='" + registration_district + '\'' +
                ", registration_address='" + registration_address + '\'' +
                ", phone='" + phone + '\'' +
                ", pin='" + pin + '\'' +
                ", katm_sir='" + katm_sir + '\'' +
                ", credit_type='" + credit_type + '\'' +
                ", summa='" + summa + '\'' +
                ", procent='" + procent + '\'' +
                ", credit_duration='" + credit_duration + '\'' +
                ", credit_exemtion='" + credit_exemtion + '\'' +
                ", currency='" + currency + '\'' +
                ", live_address='" + live_address + '\'' +
                ", live_cadastr='" + live_cadastr + '\'' +
                ", registration_cadastr='" + registration_cadastr + '\'' +
                '}';
    }
}
