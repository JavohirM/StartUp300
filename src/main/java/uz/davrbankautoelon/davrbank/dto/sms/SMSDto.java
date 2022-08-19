package uz.davrbankautoelon.davrbank.dto.sms;

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
public class SMSDto implements Serializable {

    @JsonProperty("originator")
    private String originator;

    @JsonProperty("content")
    private ContextDto content;


}
