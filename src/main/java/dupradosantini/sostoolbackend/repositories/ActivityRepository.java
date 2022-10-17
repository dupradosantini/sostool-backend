package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.Activity;
import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("SELECT a FROM Activity a WHERE a.workspace.id =:workspace_id")
    List<Activity> findActivitiesByWorkspaceId(@Param("workspace_id") Integer workspace_id);

    @Query("SELECT a.workspaceMember FROM Activity a WHERE a.id =:activity_id")
    Set<WorkspaceMember> findMembersInActivity(@Param("activity_id") Integer activity_id);

}
