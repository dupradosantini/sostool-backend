package dupradosantini.sostoolbackend.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

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


    @NotEmpty(message = "This field is required")
    @Length(min = 3, max = 100, message = "Role name has to have between 3 and 100 characters")
    private String name;

    @Length(max=2000, message = "Workspace description can have at most 2000 characters")
    private String description;

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
