package uz.davrbankautoelon.davrbank.dto.auth.moral;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoralImagesDto implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("certificate")
    private String certificate;
    @JsonProperty("passport")
    private String passport;
    @JsonProperty("document1")
    private List<SecondaryImage> document1;
    @JsonProperty("document2")
    private String document2;
    @JsonProperty("file")
    private String file;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SecondaryImage{
        @JsonProperty("image_path")
        private String imagePath;
    }
}
