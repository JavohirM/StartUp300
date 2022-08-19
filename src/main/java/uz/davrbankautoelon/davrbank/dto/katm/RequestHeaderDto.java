package uz.davrbankautoelon.davrbank.dto.katm;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeaderDto implements Serializable {
    private String code;
    private String type;
}
