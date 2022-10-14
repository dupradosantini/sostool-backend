package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("SELECT a FROM Activity a WHERE a.workspace.id =:workspace_id")
    List<Activity> findActivitiesByWorkspaceId(@Param("workspace_id") Integer workspace_id);

}
