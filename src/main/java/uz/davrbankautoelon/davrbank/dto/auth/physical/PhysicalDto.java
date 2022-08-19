package uz.davrbankautoelon.davrbank.dto.auth.physical;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbankautoelon.davrbank.dto.GenericDto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalDto extends GenericDto {
    private String first_name;
    private String last_name;
    private String patronymic;
    private String serialNumber;
    private String passportNumber;
    private String status;
    private String phoneNumber;
    private String summa;
    private String client_type;
    private String birthDate;
    private String branch;
    private String subBranch;
    private String requestId;
    private MultipartFile[] images;
    private String image_path;
}
