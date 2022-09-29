package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "This field is required")
    @Length(min = 3, max = 100, message = "BusinessRole name has to have between 3 and 100 characters")
    private String name;

    @Length(max=2000, message = "BusinessRole description can have at most 2000 characters")
    private String description;


    @ManyToMany(mappedBy = "teamAssignedRoles")
    @JsonIgnore
    private Set<Team> teams; //Teams that such role is assigned to

    @ManyToOne(optional = false)
    @JsonBackReference(value = "workspace-businessrole")
    private Workspace workspace; //Workspace that this role exists.

    @ManyToOne(optional = false)
    @JsonBackReference(value = "parentRole-sonRoles")
    private ModelRole parentRole;

    @ManyToMany
    private Set<BusinessResponsibility> roleAssignedResponsibilities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessRole")
    @JsonManagedReference(value = "businessRole-assignedMembers")
    private Set<WorkspaceMember> assignedMembers;

    @Override
    public String toString() {
        return "BusinessRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teams=" + teams +
                ", workspace=" + workspace +
                ", parentRole=" + parentRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessRole that = (BusinessRole) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
