package dupradosantini.sostoolbackend.domain;

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
public class ModelRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true)
    @NotEmpty(message = "This field is required")
    @Length(min = 3, max = 100, message = "ModelRole name has to have between 3 and 100 characters")
    private String name;

    @Length(max=2000, message = "ModelRole description can have at most 2000 characters")
    private String description;

    @OneToMany
    @JsonManagedReference(value = "parentRole-sonRoles")
    private Set<BusinessRole> sonRoles;

    public ModelRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelRole modelRole = (ModelRole) o;
        return id.equals(modelRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
