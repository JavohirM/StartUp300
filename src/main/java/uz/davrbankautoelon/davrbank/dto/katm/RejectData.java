package uz.davrbankautoelon.davrbank.dto.katm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectData implements Serializable {
    @JsonProperty("pHead")
    private String pHead;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pDeclineDate")
    private String pDeclineDate;
    @JsonProperty("pClaimId")
    private String pClaimId;
    @JsonProperty("pDeclineNumber")
    private String pDeclineNumber;
    @JsonProperty("pDeclineReason")
    private String pDeclineReason;
    @JsonProperty("pDeclineReasonNote")
    private String pDeclineReasonNote;
}
