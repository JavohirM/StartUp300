package uz.davrbankautoelon.davrbank.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class _Role extends Auditable {

    @Column(name = "name")
    private String name;
}
