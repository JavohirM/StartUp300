package uz.davrbankautoelon.davrbank.controller.katm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.katm.KATMBodyDto;
import uz.davrbankautoelon.davrbank.dto.katm.KATMDto;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.mib_katm.MiBKATMService;

@RestController
public class KATMController {

    private final MiBKATMService service;

    @Value("${URI_DETAILS_KATM}")
    private String URI_DETAILS_KATM;

    public KATMController(MiBKATMService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/v1/katm/details", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Object>> getDetails(@RequestBody KATMDto dto){
        KATMBodyDto detail = new KATMBodyDto();
        detail.setBranch(dto.getBranch());
        detail.setClient_type("0");
        detail.setReport_id("021");
        detail.setClaim_id(dto.getId());
        return service.getDetails(detail, URI_DETAILS_KATM);
    }
}
