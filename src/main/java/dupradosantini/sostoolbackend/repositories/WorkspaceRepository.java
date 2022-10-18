package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.BusinessResponsibility;
import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

    //  Finds all teams that belong to a certain workspace.
    @Query("SELECT t FROM Team t WHERE t.workspace.id = :workspace_id")
    Set<Team> findTeamsOfWorkspace(@Param("workspace_id") Integer workspace_id);

    @Query("SELECT t FROM Team t WHERE t.workspace.id = :workspace_id AND t.id = :team_id")
    Optional<Team> findSingleTeam(@Param("workspace_id") Integer workspace_id, @Param("team_id") Integer team_id);

    @Query("SELECT b FROM BusinessRole b WHERE size(b.teams) > 1 AND b.workspace.id = :workspace_id")
    Optional<List<BusinessRole>> findRolesInMoreThanOne(@Param("workspace_id") Integer workspace_id);

    @Query("SELECT b FROM BusinessRole b WHERE b.name = :business_name AND b.workspace.id = :workspace_id")
    Optional<BusinessRole> findRoleWithNameInWorkspace(@Param("business_name") String business_name, @Param("workspace_id") Integer workspace_id);

    @Query("SELECT r FROM BusinessResponsibility r WHERE r.description = :responsibility_desc AND r.workspace.id = :workspace_id")
    Optional<BusinessResponsibility> findResponsibilityWithDescriptionInWorkspace(
            @Param("responsibility_desc")String responsibility_desc,
            @Param("workspace_id") Integer workspace_id);
}
