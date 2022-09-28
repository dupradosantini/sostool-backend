package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponsibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min=2, max=2000, message = "ModelResponsibility description can have at most 2000 characters")
    private String description;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "parentResponsibility-sonResponsibilities")
    private ModelResponsibility parentResponsibility;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "workspace-businessResponsibilities")
    private Workspace workspace; //Workspace that this responsibility exists.

    @ManyToMany(mappedBy = "roleAssignedResponsibilities")
    @JsonIgnore
    private Set<BusinessRole> businessRoles; //Roles that such responsibility is assigned to

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessResponsibility that = (BusinessResponsibility) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "BusinessResponsibility{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", parentResponsibility=" + parentResponsibility +
                '}';
    }
}
