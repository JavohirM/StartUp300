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
public class MessagesDto implements Serializable {

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("message-id")
    private String messageID;

    @JsonProperty("sms")
    private SMSDto sms;
}
