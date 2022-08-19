package uz.davrbankautoelon.davrbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbankautoelon.davrbank.model.auth._Role;

public interface IRoleRepository extends JpaRepository<_Role, Long> {
    _Role findByName(String name);
}
