package uz.davrbankautoelon.davrbank.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto implements Serializable {

    private String date;
    private String status;
    private String name;
    private String branch;
    private String client_type;
}
