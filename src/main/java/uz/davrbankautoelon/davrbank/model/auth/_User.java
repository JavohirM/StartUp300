package uz.davrbankautoelon.davrbank.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.davrbankautoelon.davrbank.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class _User extends Auditable {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "branch")
    private String branch;
    @Column(name = "sub_branch")
    private String subBranch;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<_Role> roles = new ArrayList<>();  // ROLE_USER, ROLE_ADMIN

    @Column(name = "is_active")
    private boolean isActive;

}
