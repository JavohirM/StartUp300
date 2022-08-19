package uz.davrbankautoelon.davrbank.controller.request.moral;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davrbankautoelon.davrbank.dto.auth.ResponseDto;
import uz.davrbankautoelon.davrbank.dto.auth.SearchDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralViewDto;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.auth.request.moral.MoralGetService;
import uz.davrbankautoelon.davrbank.service.auth.request.moral.MoralService;
import uz.davrbankautoelon.davrbank.service.auth.search.SearchService;

@RestController
public class MoralController {

    private final MoralService service;
    private final MoralGetService getService;
    private final SearchService searchService;

    public MoralController(MoralService service, MoralGetService getService, SearchService searchService) {
        this.service = service;
        this.getService = getService;
        this.searchService = searchService;
    }

    @RequestMapping(value = "api/v1/request/moral/save", method = RequestMethod.POST)
    public ResponseEntity<DataDto<ResponseDto>> save(@RequestBody MoralDto dto){
        return service.create(dto);
    }


    @RequestMapping(value = "api/v1/request/moral/get", method = RequestMethod.GET)
    public ResponseEntity<DataDto<?>> getAll(){
        return getService.getAll();
    }

    @RequestMapping(value = "api/v1/request/search", method = RequestMethod.POST)
    public ResponseEntity<DataDto<?>> search(@RequestBody SearchDto dto){

        if(dto.getClient_type().equals("08")){
            return new ResponseEntity<>(new DataDto<>(searchService.searchPhysical(dto), true), HttpStatus.OK);
        }
        if(dto.getClient_type().equals("09")){
            return new ResponseEntity<>(new DataDto<>(searchService.searchMoral(dto), true), HttpStatus.OK);
        }
        return null;
    }


    @RequestMapping(value = "api/v1/request/moral/get/by", method = RequestMethod.GET)
    public ResponseEntity<DataDto<?>> getById(@RequestParam String id){
        return getService.getById(id);
    }


    @RequestMapping(value = "api/v1/request/moral/get/branch/{branch}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<?>> getByBranch(@PathVariable String branch){
        return getService.getByBranch(branch);
    }
}
