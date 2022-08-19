package uz.davrbankautoelon.davrbank.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidAndRejectRequestDto implements Serializable {
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("reject_message")
    private String rejectMessage;
}
