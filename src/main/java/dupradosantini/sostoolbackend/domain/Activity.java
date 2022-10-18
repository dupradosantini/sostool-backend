package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Activity name field is required")
    @Length(min = 3, max = 80, message = "Actitivity name must be between 3 and 80 characters")
    private String name;

    @Length( max = 300, message = "Actitivity name must be between 3 and 300 characters")
    private String description;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "workspace-activity")
    private  Workspace workspace;

    @ManyToMany
    @JsonIgnore
    private Set<WorkspaceMember> workspaceMember;

    @Enumerated(EnumType.STRING)
    private ActivityState state;

    public Activity(String name) {
        this.name = name;
        this.state = ActivityState.NOT_STARTED;
    }

}
