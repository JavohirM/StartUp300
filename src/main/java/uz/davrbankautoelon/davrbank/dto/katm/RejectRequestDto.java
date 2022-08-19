package uz.davrbankautoelon.davrbank.dto.katm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RejectRequestDto implements Serializable {

    @JsonProperty("security")
    private RejectSecurity security;
    @JsonProperty("data")
    private RejectData data;

}
