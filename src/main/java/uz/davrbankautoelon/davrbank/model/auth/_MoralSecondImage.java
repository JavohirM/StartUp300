package uz.davrbankautoelon.davrbank.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "secondary_image")
public class _MoralSecondImage extends Auditable {

    private String imagePath;
}
