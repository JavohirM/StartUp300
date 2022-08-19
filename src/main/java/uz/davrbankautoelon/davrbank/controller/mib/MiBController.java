package uz.davrbankautoelon.davrbank.controller.mib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.mib.MiBSearchDto;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.mib_katm.MiBKATMService;

@RestController
public class MiBController {

    private final MiBKATMService service;
    @Value("${URI_DETAILS_MIB}")
    private String URI_DETAILS_MIB;

    public MiBController(MiBKATMService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/v1/mib/details", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Object>> login(@RequestBody MiBSearchDto detail){
        return service.getDetails(detail,URI_DETAILS_MIB);
    }
}
