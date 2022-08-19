package uz.davrbankautoelon.davrbank.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSMS implements Serializable {
    @JsonProperty("error-code")
    private String error;
    @JsonProperty("error-description")
    private String description;
}
