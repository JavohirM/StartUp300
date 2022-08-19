package uz.davrbankautoelon.davrbank.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoDto implements Serializable {

    private String serial;
    private String number;
    private String date;
}
