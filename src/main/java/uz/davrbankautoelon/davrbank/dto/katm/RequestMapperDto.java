package uz.davrbankautoelon.davrbank.dto.katm;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMapperDto implements Serializable {

    private String request;
    private String response;
    private String requestId;
}
