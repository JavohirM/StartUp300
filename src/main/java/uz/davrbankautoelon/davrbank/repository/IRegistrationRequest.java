package uz.davrbankautoelon.davrbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbankautoelon.davrbank.model.auth._Request;

public interface IRegistrationRequest extends JpaRepository<_Request, Long> {
}
