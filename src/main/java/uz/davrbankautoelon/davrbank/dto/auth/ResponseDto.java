package uz.davrbankautoelon.davrbank.dto.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {

    private ErrorDto error;
    private String message;
    private Object smsMessage;
}
