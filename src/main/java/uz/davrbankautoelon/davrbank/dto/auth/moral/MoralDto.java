package uz.davrbankautoelon.davrbank.dto.auth.moral;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoralDto implements Serializable {

    @JsonProperty("inn")
    private String inn;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("subBranch")
    private String subBranch;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("client_type")
    private String client_type;
    @JsonProperty("images")
    private List<MoralImagesDto> images;
}
