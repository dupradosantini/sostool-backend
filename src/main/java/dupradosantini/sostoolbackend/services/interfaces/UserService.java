package dupradosantini.sostoolbackend.services.interfaces;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.domain.dtos.RoleHistoryDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    AppUser findById(Integer userId);

    List<AppUser> findAllUsers();

    AppUser createUser(AppUser appUserObj);

    List<RoleHistoryDto> findUserRoleHistory(Integer userId);

    Set<AppUser> findCurrentWorkspaceUsers(Integer workspaceId);
}
