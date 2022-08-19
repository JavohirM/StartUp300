package uz.davrbankautoelon.davrbank.service.auth.request.moral;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.dto.auth.ErrorDto;
import uz.davrbankautoelon.davrbank.dto.auth.ResponseDto;
import uz.davrbankautoelon.davrbank.repository.MoralRepository;
import uz.davrbankautoelon.davrbank.response.DataDto;

@Service
@Slf4j
public class MoralGetService {


    private final MoralRepository repository;

    public MoralGetService(MoralRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<DataDto<?>> getAll(){
        try{
            return new ResponseEntity<>(new DataDto<>(repository.getAll(null), true), HttpStatus.OK);
        } catch (Exception e){
            ResponseDto responseDto = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            log.info("Error during getting all");
            errorDto.setCode("500");
            errorDto.setName("Error while getting : " + e.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<?>> getById(String id) {
        try{
            return new ResponseEntity<>(new DataDto<>(repository.getById(id), true), HttpStatus.OK);
        } catch (Exception e){
            ResponseDto responseDto = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            log.info("Error during getting all");
            errorDto.setCode("500");
            errorDto.setName("Error while getting : " + e.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<?>> getByBranch(String branch){
        try{
            return new ResponseEntity<>(new DataDto<>(repository.getAll(branch), true), HttpStatus.OK);
        } catch (Exception e){
            ResponseDto responseDto = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            log.info("Error during getting all");
            errorDto.setCode("500");
            errorDto.setName("Error while getting : " + e.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
