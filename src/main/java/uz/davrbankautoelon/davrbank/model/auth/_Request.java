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
@ToString
@Table(name = "registration_request")
public class _Request extends Auditable {
    @Column(name = "request", length = 3000)
    private String request;
    @Column(name = "response", length = 3000)
    private String response;
    @Column(name = "request_id")
    private String requestId;
    @Column(name = "reject", length = 1000)
    private String reject;
}
