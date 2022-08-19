package uz.davrbankautoelon.davrbank.dto.katm;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto implements Serializable {

    private RequestHeaderDto header;
    private RequestBodyDto request;

    @Override
    public String toString() {
        return "RegistrationRequestDto{" +
                "header=" + header +
                ", request=" + request +
                '}';
    }
}
