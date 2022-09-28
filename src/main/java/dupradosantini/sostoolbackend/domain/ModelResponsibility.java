package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class ModelResponsibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min=2, max=2000, message = "ModelResponsability description can have at most 2000 characters")
    private String description;

    @OneToMany
    @JsonManagedReference(value = "parentResponsibility-sonResponsibilities")
    private Set<BusinessResponsibility> sonResponsibilities;

    public ModelResponsibility(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelResponsibility that = (ModelResponsibility) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ModelResponsibility{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", sonResponsibilities=" + sonResponsibilities +
                '}';
    }
}
