package uz.davrbankautoelon.davrbank.service.auth.request.physical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;
import uz.davrbankautoelon.davrbank.repository.auth.KIARepository;

import java.util.List;

@Service
@Slf4j
public class KIAService {

    private final KIARepository repository;

    public KIAService(KIARepository repository) {
        this.repository = repository;
    }


    public ResponseEntity<List<PhysicalViewDto>> getList(){
        log.info("Call KIARepository");
        return new ResponseEntity<>(repository.getList(), HttpStatus.OK);
    }
}
