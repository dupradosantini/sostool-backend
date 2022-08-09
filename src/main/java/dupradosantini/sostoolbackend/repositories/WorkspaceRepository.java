package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

    //  Finds all teams that belong to a certain workspace.
    @Query("SELECT t FROM Team t WHERE t.workspace.id = :workspace_id")
    Set<Team> findTeamsOfWorkspace(@Param("workspace_id") Integer workspace_id);
}
