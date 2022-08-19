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
public class MiBSearchDto implements Serializable {
    @JsonProperty("doc_series")
    private String doc_series;
    @JsonProperty("doc_number")
    private String doc_number;
}
