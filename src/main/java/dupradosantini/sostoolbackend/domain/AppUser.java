package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 60, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "Campo email é obrigatório")
    @Length(max = 70, message = "O email deve ter no máximo 30 caracteres")
    private String email;

    @Length(max=200, message = "Senhas de até 200 caracteres")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    @JsonManagedReference(value = "appUser-workspaceMember")
    private Set<WorkspaceMember> workspaceMember;

    public AppUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
