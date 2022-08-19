package uz.davrbankautoelon.davrbank.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "moral_image")
public class _MoralImage extends Auditable {
    @Column(name = "certificate")
    private String certificate;
    @Column(name = "passport")
    private String passport;
    @Column(name = "document1")
    @OneToMany(targetEntity = _MoralSecondImage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_secondary_id", referencedColumnName = "id")
    private List<_MoralSecondImage> document1;
    @Column(name = "document2")
    private String document2;
    @Column(name = "file")
    private String file;
}
