package uz.davrbankautoelon.davrbank.model.auth;


import lombok.*;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auto_elon_physical")
public class _Physical extends Auditable {

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "client_type")
    private String client_type;

    @Column(name = "summa")
    private String summa;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "branch")
    private String branch;

    @Column(name = "sub_branch")
    private String subBranch;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "reject_message")
    private String reject_message;

    @Column(name = "image_path")
    private String image_path;

    @OneToMany(targetEntity = _Image.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_elon_physical_id", referencedColumnName = "id")
    private Collection<_Image> images;



}
