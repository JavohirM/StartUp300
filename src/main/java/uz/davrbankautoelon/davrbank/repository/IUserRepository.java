package uz.davrbankautoelon.davrbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbankautoelon.davrbank.model.auth._User;

public interface IUserRepository extends JpaRepository<_User, Long> {
    _User findByUsername(String username);
}
