package uz.davrbankautoelon.davrbank.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainSMSDto implements Serializable {

    @JsonProperty("messages")
    private List<MessagesDto> messages;
}
