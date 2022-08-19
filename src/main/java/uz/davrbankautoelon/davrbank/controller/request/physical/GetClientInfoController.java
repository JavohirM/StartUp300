package uz.davrbankautoelon.davrbank.controller.request.physical;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.davrbankautoelon.davrbank.dto.client.ClientInfoDto;
import uz.davrbankautoelon.davrbank.dto.client.GetInfoDto;
import uz.davrbankautoelon.davrbank.service.auth.client.ClientInfoService;

@RestController
public class GetClientInfoController {

    private final ClientInfoService service;

    public GetClientInfoController(ClientInfoService service) {
        this.service = service;
    }

    @RequestMapping(value = "api/v1/client/info", method = RequestMethod.POST)
    public ClientInfoDto info(@RequestBody GetInfoDto dto) {

        return service.getInfo(dto);

    }
}