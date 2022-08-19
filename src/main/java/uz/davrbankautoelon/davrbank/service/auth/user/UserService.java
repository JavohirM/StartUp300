package uz.davrbankautoelon.davrbank.service.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.davrbankautoelon.davrbank.dto.user.RoleDto;
import uz.davrbankautoelon.davrbank.dto.user.UserDto;
import uz.davrbankautoelon.davrbank.mapper.auth.RoleMapper;
import uz.davrbankautoelon.davrbank.mapper.auth.UserMapper;
import uz.davrbankautoelon.davrbank.model.auth._Role;
import uz.davrbankautoelon.davrbank.model.auth._User;
import uz.davrbankautoelon.davrbank.repository.IRoleRepository;
import uz.davrbankautoelon.davrbank.repository.IUserRepository;
import uz.davrbankautoelon.davrbank.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService, UserDetailsService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }


    @Override
    public _User save(UserDto user) {
        log.info("Saving new user {} to database,", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        _User result = userMapper.fromDto(user);
        return userRepository.save(result);
    }

    @Override
    public _Role saveRole(RoleDto role) {
        log.info("Savin new role {} to user ", role.getName());
        return roleRepository.save(roleMapper.fromDto(role));
    }

    @Override
    public void addRoleToUser(String name, String roleName) {
        log.info("Adding role {} to user {}", roleName, name);
        _User user = userRepository.findByUsername(name);
        _Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public _User getUser(String user) {
        log.info("Fetching user {}", user);
        return userRepository.findByUsername(user);
    }

    @Override
    public List<_User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        _User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}
