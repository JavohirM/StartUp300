package uz.davrbankautoelon.davrbank.service;

import org.springframework.http.ResponseEntity;
import uz.davrbankautoelon.davrbank.response.DataDto;

public interface IMainService<T, D> {

    ResponseEntity<DataDto<T>> create(D dto);

}
