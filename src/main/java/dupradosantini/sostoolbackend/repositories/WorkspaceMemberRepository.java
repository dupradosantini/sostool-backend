package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Integer> {

    @Query("SELECT wm FROM WorkspaceMember wm WHERE (wm.appUser.id =:userId) AND (wm.businessRole.id=:businessRoleId) AND (wm.endDate IS NULL)")
    Optional<WorkspaceMember> findWorkspaceMemberByAppUserAndBusinessRole(@Param("userId")Integer userId, @Param("businessRoleId")Integer businessRoleId);

    @Query("SELECT a.appUser FROM WorkspaceMember a WHERE a.businessRole.id =:role_id AND (a.endDate IS null)")
    Set<AppUser> findUsersWithRole(@Param("role_id") Integer role_id);

    @Query("SELECT a FROM WorkspaceMember a WHERE (a.workspace.id =:workspace_id AND a.appUser.id=:user_id)")
    Set<WorkspaceMember> findUserWithRoleInWorkspace(@Param("workspace_id") Integer workspace_id,@Param("user_id") Integer user_id);

    @Query("SELECT a FROM WorkspaceMember a WHERE (a.appUser.id=:user_id)")
    Set<WorkspaceMember> findUserRoles(@Param("user_id") Integer user_id);

    @Query("SELECT a.appUser FROM WorkspaceMember a WHERE (a.workspace.id =:workspace_id) AND (a.endDate IS null)")
    Set<AppUser> findCurrentUsersInWorkspace(@Param("workspace_id") Integer workspace_id);

    @Query("SELECT a FROM WorkspaceMember a WHERE (a.workspace.id =:workspace_id) AND (a.endDate IS null)")
    Set<WorkspaceMember> findCurrentWorkspaceMembersInWorkspace(@Param("workspace_id") Integer workspace_id);

}
