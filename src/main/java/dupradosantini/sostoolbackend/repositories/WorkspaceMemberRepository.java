package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Integer> {

    Optional<WorkspaceMember> findWorkspaceMemberByAppUserAndBusinessRole(AppUser user, BusinessRole businessRole);

    @Query("SELECT a.appUser FROM WorkspaceMember a WHERE a.businessRole.id =:role_id")
    Set<AppUser> findUsersWithRole(@Param("role_id") Integer role_id);

    @Query("SELECT a FROM WorkspaceMember a WHERE (a.workspace.id =:workspace_id AND a.appUser.id=:user_id)")
    Set<WorkspaceMember> findUserWithRoleInWorkspace(@Param("workspace_id") Integer workspace_id,@Param("user_id") Integer user_id);

    @Query("SELECT a FROM WorkspaceMember a WHERE (a.appUser.id=:user_id)")
    Set<WorkspaceMember> findUserRoles(@Param("user_id") Integer user_id);

}
