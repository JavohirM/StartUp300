package uz.davrbankautoelon.davrbank.dto.mib;

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
public class MiBLoginResponseDto implements Serializable {
    @JsonProperty("a_t")
    private String token;

}
