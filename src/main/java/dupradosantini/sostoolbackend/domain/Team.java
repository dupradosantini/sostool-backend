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
@ToString
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "This field is required")
    @Length(min = 3, max = 100, message = "Team name has to have between 3 and 100 characters")
    private String name;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "workspace-team")
    private Workspace workspace;

    @ManyToMany
    /*@JoinTable(name = "team-roles",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "businessrole_id"))*/
    //@JsonManagedReference(value = "team-businessrole")
    private Set<BusinessRole> teamAssignedRoles;

    public Team(String name, Workspace workspace) {
        this.name = name;
        this.workspace = workspace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return getId().equals(team.getId()) && getName().equals(team.getName()) && getWorkspace().equals(team.getWorkspace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getWorkspace());
    }
}
