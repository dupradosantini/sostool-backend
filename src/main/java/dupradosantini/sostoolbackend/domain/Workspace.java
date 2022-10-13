package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotEmpty(message = "This field is required")
    @Length(min = 3, max = 100, message = "Workspace has to have between 3 and 100 characters")
    private String name;

    @Length(max=2000, message = "Workspace description can have at most 2000 characters")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspace")
    @JsonManagedReference(value = "workspace-team")
    private Set<Team> teams;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspace")
    @JsonManagedReference(value = "workspace-businessrole")
    private Set<BusinessRole> businessRoles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspace")
    @JsonManagedReference(value = "workspace-businessResponsibilities")
    private Set<BusinessResponsibility> businessResponsibilities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspace")
    @JsonManagedReference(value = "workspace-members")
    private Set<WorkspaceMember> members;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspace")
    @JsonManagedReference(value = "workspace-activity")
    private List<Activity> activities;

    //null description constructor.
    public Workspace(String name) {
        this.name = name;
    }

    public Workspace(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workspace)) return false;
        Workspace workspace = (Workspace) o;
        return getId().equals(workspace.getId()) && getName().equals(workspace.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
