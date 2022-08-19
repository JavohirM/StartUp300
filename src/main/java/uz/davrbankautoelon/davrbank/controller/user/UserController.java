package uz.davrbankautoelon.davrbank.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.davrbankautoelon.davrbank.dto.user.UserDto;
import uz.davrbankautoelon.davrbank.model.auth._User;
import uz.davrbankautoelon.davrbank.service.auth.user.UserService;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "api/v1/user/all", method = RequestMethod.GET)
    public ResponseEntity<List<_User>> getUsers() {
        return ResponseEntity.ok().body(service.getUsers());
    }

    @RequestMapping(value = "api/v1/user/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestBody UserDto user) {
        service.save(user);
        user.getRoles().forEach(p -> {
            service.addRoleToUser(user.getUsername(), p.getName());
        });
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/save").toUriString());
        log.info(uri.getPath());
        return ResponseEntity.created(uri).body("Created");
    }

    @RequestMapping(value = "api/v1/user/{name}")
    public ResponseEntity<_User> getByName(@PathVariable String name){
        _User user = service.getUser(name);
        return ResponseEntity.ok().body(user);
    }
}
