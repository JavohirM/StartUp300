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
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String branch;
    private String subBranch;
    private String username;
    private String password;
    private Collection<RoleDto> roles;
    private boolean isActive;
}
