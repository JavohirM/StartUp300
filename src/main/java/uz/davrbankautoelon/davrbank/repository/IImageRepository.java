package uz.davrbankautoelon.davrbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbankautoelon.davrbank.model.auth._Image;

public interface IImageRepository extends JpaRepository<_Image, Long> {
}
