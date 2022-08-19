package uz.davrbankautoelon.davrbank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditable implements Serializable,BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Override
    public int hashCode() {
        return (getId() != null ? getId().intValue() : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auditable)) {
            return false;
        }
        if (getId() == null) {
            return false;
        }

        Auditable unObject = (Auditable) o;

        String table1 = getEntityName(this);
        String table2 = getEntityName(unObject);
        return !(table1 == null || !table1.equals(table2)) && getId().equals(unObject.getId());
    }

    public static String getEntityName(Auditable o) {
        if (o instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy) o;
            return proxy.getHibernateLazyInitializer().getEntityName();
        }
        Entity entity = o.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            return !"".equals(entity.name()) ? entity.name() : o.getClass().getName();
        }
        return "";
    }
}
