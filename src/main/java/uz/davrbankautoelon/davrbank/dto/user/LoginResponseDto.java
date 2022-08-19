package uz.davrbankautoelon.davrbank.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto implements Serializable {
    private String token;
    private String branch;
    private String username;
    private Collection<?> role;
}
