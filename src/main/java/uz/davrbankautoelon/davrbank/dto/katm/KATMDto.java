package uz.davrbankautoelon.davrbank.dto.katm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KATMDto implements Serializable {

    private String id;
    private String branch;
}
