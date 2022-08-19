package uz.davrbankautoelon.davrbank.mapper.auth;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.user.UserDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._User;

@Component
public class UserMapper implements MapStructMapper<UserDto, _User> {


    @Override
    public _User fromDto(UserDto dto) {
        _User user = new _User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setSubBranch(dto.getSubBranch());
        user.setBranch(dto.getBranch());
        user.setPassword(dto.getPassword());
        user.setActive(dto.isActive());
        return user;
    }
}
