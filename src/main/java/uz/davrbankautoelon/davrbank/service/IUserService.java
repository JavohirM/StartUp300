package uz.davrbankautoelon.davrbank.service;

import uz.davrbankautoelon.davrbank.dto.user.RoleDto;
import uz.davrbankautoelon.davrbank.dto.user.UserDto;
import uz.davrbankautoelon.davrbank.model.auth._Role;
import uz.davrbankautoelon.davrbank.model.auth._User;

import java.util.List;

public interface IUserService {
    _User save(UserDto user);
    _Role saveRole(RoleDto role);
    void addRoleToUser(String name, String roleName);
    _User getUser(String user);
    List<_User> getUsers();
}
