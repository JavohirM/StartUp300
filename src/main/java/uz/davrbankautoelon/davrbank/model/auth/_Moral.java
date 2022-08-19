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
@Table(name = "_moral")
public class _Moral extends Auditable {
    @Column(name = "inn")
    private String inn;
    @Column(name = "branch")
    private String branch;
    @Column(name = "sub_branch")
    private String subBranch;
    @Column(name = "phone")
    private String phone;
    @Column(name = "amount")
    private String amount;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "client_type")
    private String client_type;
    @Column(name = "reject_message")
    private String reject_message;
    @OneToMany(targetEntity = _MoralImage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "moral_image_id", referencedColumnName = "id")
    private List<_MoralImage> images;
    @Column(name = "request_id")
    private String requestId;

}
