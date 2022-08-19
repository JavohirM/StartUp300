package uz.davrbankautoelon.davrbank.dto.auth.physical;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalViewDto {
    private String id;
    private String first_name;
    private String created_date;
    private String last_name;
    private String patronymic;
    private String inps;
    private String client_type;
    private String serialNumber;
    private String passportNumber;
    private String status;
    private String phoneNumber;
    private String region;
    private String summa;
    private String branch;
    private String typeCar;
    private String subBranch;
    private String scoring;
    private String birthDate;
    private String requestId;
    private String reject_message;
    private List<ImageDto> images;
    private String image_path;
}
