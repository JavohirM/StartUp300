package uz.davrbankautoelon.davrbank.dto.katm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectSecurity implements Serializable {

    @JsonProperty("pLogin")
    @SerializedName(value = "pLogin", alternate = {"pLogin"})
    private String pLogin;
    @JsonProperty("pPassword")
    @SerializedName(value = "pPassword", alternate = {"pPassword"})
    private String pPassword;
}
