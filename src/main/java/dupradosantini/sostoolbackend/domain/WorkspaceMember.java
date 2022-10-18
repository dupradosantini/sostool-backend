package dupradosantini.sostoolbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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

    @ManyToMany
    private Set<Activity> activities;

    @NotNull
    private Date startDate;

    private Date endDate;

    public WorkspaceMember(AppUser appUser, BusinessRole businessRole, Workspace workspace, Date startDate) {
        this.appUser = appUser;
        this.businessRole = businessRole;
        this.workspace = workspace;
        this.startDate = startDate;
    }
}
