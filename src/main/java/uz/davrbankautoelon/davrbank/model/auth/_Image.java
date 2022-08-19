package uz.davrbankautoelon.davrbank.model.auth;

import lombok.*;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image")
public class _Image extends Auditable {

   @Column(name = "path_image")
   private String pathToImage;

}
