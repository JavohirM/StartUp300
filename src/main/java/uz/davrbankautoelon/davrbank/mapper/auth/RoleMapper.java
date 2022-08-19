package uz.davrbankautoelon.davrbank.mapper.auth;

import org.springframework.stereotype.Component;
import uz.davrbankautoelon.davrbank.dto.user.RoleDto;
import uz.davrbankautoelon.davrbank.mapper.MapStructMapper;
import uz.davrbankautoelon.davrbank.model.auth._Role;

@Component
public class RoleMapper implements MapStructMapper<RoleDto, _Role> {
    @Override
    public _Role fromDto(RoleDto dto) {
        _Role role = new _Role();
        role.setName(dto.getName());
        return role;
    }
}
