package dupradosantini.sostoolbackend.services.interfaces;

import dupradosantini.sostoolbackend.domain.*;

import java.util.List;
import java.util.Set;

public interface WorkspaceService {
    Workspace findById(Integer id);

    List<Workspace> findAll();

    Set<Team> findTeams(Integer id);

    Workspace create(Workspace obj);

    void delete(Integer id);

    Team createTeam(Integer workspaceId, Team obj);

    void deleteTeam(Integer workspaceId, Integer teamId);

    BusinessRole createRole(Integer workspaceId, BusinessRole obj);

    Team getTeamInWorkspace(Integer workspaceId, Integer teamId);

    Set<Team> assignRoleToTeam(Integer workspaceId, Integer teamId, Integer roleId);

    //ROLES

    BusinessRole findRoleById(Integer roleId);

    Set<BusinessRole> findAllRoles(Integer workspaceId);

    List<BusinessRole> businessRoleExistsInManyTeams(Integer workspaceId);

    //RESPONSIBILITY

    BusinessResponsibility findResponsibilityById(Integer responsibilityId);

    Set<BusinessResponsibility> findAllResponsibilities(Integer workspaceId);

    BusinessResponsibility createResponsibility(Integer workspaceId, BusinessResponsibility obj);

    Set<BusinessRole> assignResponsibilityToRole(Integer workspaceId, Integer roleId, Integer responsibilityId);

    Set<BusinessResponsibility> removeResponsibilityOfRole(Integer workspaceId, Integer roleId, Integer responsibilityId);

    Set<AppUser> assignUserToRole(Integer workspaceId, Integer roleId, Integer memberId);

    Set<AppUser> findUsersWithRole(Integer workspaceId, Integer roleId);
}
