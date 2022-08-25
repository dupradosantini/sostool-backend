package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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






}
