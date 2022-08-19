package uz.davrbankautoelon.davrbank.controller.request.physical;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalDto;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;
import uz.davrbankautoelon.davrbank.dto.auth.SearchDto;
import uz.davrbankautoelon.davrbank.dto.auth.ValidAndRejectRequestDto;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.request.physical.PhysicalService;

import java.util.List;

@RestController
public class PhysicalController {


    private final PhysicalService service;


    public PhysicalController(PhysicalService service) {
        this.service = service;

    }


    @RequestMapping(value = "api/v1/request/physical/save", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@ModelAttribute PhysicalDto dto) {
        return service.create(dto);
    }

    @RequestMapping(value = "api/v1/valid", method = RequestMethod.POST)
    public ResponseEntity<?> valid(@RequestBody ValidAndRejectRequestDto dto) {
        return service.valid(dto.getPhoneNumber(), dto.getRequestId(), dto.getBranch());
    }

    @RequestMapping(value = "api/v1/reject", method = RequestMethod.POST)
    public ResponseEntity<?> reject(@RequestBody ValidAndRejectRequestDto dto) {
        return service.reject(dto.getPhoneNumber(), dto.getRequestId(), dto.getBranch(), dto.getRejectMessage());
    }

    @RequestMapping(value = "api/v1/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getById(id);
    }



    @RequestMapping(value = "api/v1/request/physical/get", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        System.out.println("Clallllalsdasd");
        return service.getAll();
    }

    @RequestMapping(value = "api/v1/request/physical/get/branch/{branch}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByBranch(@PathVariable String branch) {
        System.out.println("Clallllalsdasd");
        return service.getAllByBranch(branch);
    }


    @RequestMapping(value = "api/v1/search", method = RequestMethod.POST)
    public ResponseEntity<DataDto<List<PhysicalViewDto>>> search(@RequestBody SearchDto dto) {
        return service.search(dto);
    }

    @RequestMapping(value = "api/v1/get/by/branch", method = RequestMethod.POST)
    public ResponseEntity<DataDto<List<PhysicalViewDto>>> getByBranch(@RequestBody SearchDto dto) {
        return service.getByBranch(dto.getBranch());
    }

    @RequestMapping(value = "api/v1/search/branch", method = RequestMethod.POST)
    public ResponseEntity<DataDto<List<PhysicalViewDto>>> searchByBranch(@RequestBody SearchDto dto) {
        return service.searchByBranch(dto);
    }
}


