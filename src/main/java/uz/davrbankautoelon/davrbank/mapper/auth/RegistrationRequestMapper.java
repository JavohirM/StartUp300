package uz.davrbankautoelon.davrbank.mapper.auth;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.katm.RequestMapperDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._Request;

@Component
public class RegistrationRequestMapper implements MapStructMapper<RequestMapperDto, _Request> {

    @Override
    public _Request fromDto(RequestMapperDto dto) {
        _Request request = new _Request();
        request.setRequest(dto.getRequest());
        request.setResponse(dto.getResponse());
        request.setRequestId(dto.getRequestId());
        return request;
    }
}
