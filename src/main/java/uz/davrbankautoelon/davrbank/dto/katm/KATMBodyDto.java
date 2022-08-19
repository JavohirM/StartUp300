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
public class KATMBodyDto implements Serializable {

    private String branch;
    private String client_type;
    private String claim_id;
    private String report_id;

}
