package uz.davrbankautoelon.davrbank.dto.mib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiBLoginDto implements Serializable {
    private String username;
    private String password;
}
