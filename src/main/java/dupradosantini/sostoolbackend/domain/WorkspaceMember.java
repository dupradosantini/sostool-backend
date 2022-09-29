package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMember implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "appUser-workspaceMember")
    private AppUser appUser;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "businessRole-assignedMembers")
    private BusinessRole businessRole;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "workspace-members")
    private Workspace workspace;

    @NotEmpty
    private Date startDate;

    private Date endDate;

}
