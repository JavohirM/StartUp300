package uz.davrbankautoelon.davrbank.controller.request.physical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;
import uz.davrbankautoelon.davrbank.service.auth.request.physical.KIAService;

import java.util.List;

@RestController
@Slf4j
public class KIAController {

    private final KIAService service;


    public KIAController(KIAService service) {
        this.service = service;
    }

    @RequestMapping(value = "api/v1/get/kia", method = RequestMethod.GET)
    public ResponseEntity<List<PhysicalViewDto>> getList(){
        return service.getList();
    }
}
