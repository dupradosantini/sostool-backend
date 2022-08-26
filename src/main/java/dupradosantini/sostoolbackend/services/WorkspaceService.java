package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;

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

    Team assignRoleToTeam(Integer workspaceId, Integer teamId, Integer roleId);

    BusinessRole findRoleById(Integer roleId);

    Set<BusinessRole> findAllRoles(Integer workspaceId);
}
