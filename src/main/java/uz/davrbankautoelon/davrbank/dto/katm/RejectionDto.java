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
public class RejectionDto implements Serializable {

    @JsonProperty("security")
    @SerializedName(value = "security", alternate = {"security"})
    private RejectSecurity security;
    @JsonProperty("data")
    @SerializedName(value = "data", alternate = {"data"})
    private RejectData data;
}
