package uz.davrbankautoelon.davrbank.dto.auth.moral;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoralViewDto implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("sub_branch")
    private String sub_branch;
    @JsonProperty("phoneNumber")
    private String phone;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("name")
    private String name;
    @JsonProperty("client_type")
    private String client_type;
    @JsonProperty("requestId")
    private String request_id;
    @JsonProperty("images")
    private List<MoralImagesDto> images;
}
