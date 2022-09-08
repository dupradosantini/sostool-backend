package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.ModelRole;
import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.repositories.BusinessRoleRepository;
import dupradosantini.sostoolbackend.repositories.TeamRepository;
import dupradosantini.sostoolbackend.repositories.WorkspaceRepository;
import dupradosantini.sostoolbackend.services.exceptions.BusinessRoleAlreadyExistsException;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService{

    private final BusinessRoleRepository businessRoleRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TeamRepository teamRepository;
    private final ModelRoleServiceImpl modelRoleService;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository,
                                TeamRepository teamRepository,
                                BusinessRoleRepository businessRoleRepository,
                                ModelRoleServiceImpl modelRoleService) {
        this.workspaceRepository = workspaceRepository;
        this.teamRepository = teamRepository;
        this.businessRoleRepository = businessRoleRepository;
        this.modelRoleService = modelRoleService;
    }

    @Override
    public Workspace findById(Integer id) {
        log.debug("Im in the workspace service, findById Method");
        Optional<Workspace> obj = workspaceRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Workspace not found!!"));
    }

    @Override
    public Workspace create(Workspace obj) {
        obj.setId(null);
        obj.setTeams(null);
        return workspaceRepository.save(obj);
    }

    @Override
    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        workspaceRepository.deleteById(id);
    }

    @Override
    public Set<Team> findTeams(Integer id) {
        return workspaceRepository.findTeamsOfWorkspace(id);
    }

    @Override
    public Team createTeam(Integer workspaceId, Team obj) {
        obj.setId(null);
        obj.setWorkspace(this.findById(workspaceId));
        return teamRepository.save(obj);
    }

    @Override
    public void deleteTeam(Integer workspaceId, Integer teamId) throws ObjectNotFoundException {
        try{
            Optional<Team> soughtTeam = workspaceRepository.findSingleTeam(workspaceId,teamId);
            teamRepository.deleteById(teamId);
        }catch(Exception e) {
            throw new ObjectNotFoundException("Team or workspace not found!");
        }
    }

    @Override
    public BusinessRole createRole(Integer workspaceId, BusinessRole obj) {
        obj.setId(null);
        obj.setWorkspace(this.findById(workspaceId));

        Optional<BusinessRole> testIfExists = workspaceRepository.findRoleWithNameInWorkspace(obj.getName(), workspaceId);

        if(testIfExists.isPresent()){
            throw new BusinessRoleAlreadyExistsException("Business Role with this name already exists in this workspace");
        }

        //Checking if the parentRole exists.
        Integer parentRoleId = obj.getParentRole().getId();
        ModelRole returnedParent = modelRoleService.findById(parentRoleId);
        //If it does, set the parent as the returned OBJ
        obj.setParentRole(returnedParent);

        //Also adding the role as a son to the parent.
        Set<BusinessRole> businessRoleSet = returnedParent.getSonRoles();
        businessRoleSet.add(obj);
        returnedParent.setSonRoles(businessRoleSet);

        //Saving the new businessRole FIRST, then updating the modelRole its basedOn;
        BusinessRole newObj = businessRoleRepository.save(obj);
        modelRoleService.update(returnedParent);

        return newObj;
    }

    @Override
    public Set<Team> assignRoleToTeam(Integer workspaceId, Integer teamId, Integer roleId) {
        findById(workspaceId); //checando se o workspace existe
        Set<Team> teamSet = this.findTeams(workspaceId);
        BusinessRole role = this.findRoleById(roleId);
        Set<Team> roleTeams = role.getTeams();
        for(Team actual : teamSet){
            if(actual.getId().equals(teamId)){
                Set<BusinessRole> currentRoles = actual.getTeamAssignedRoles();
                if(actual.getWorkspace().equals(role.getWorkspace())){
                    if(actual.getTeamAssignedRoles().contains(role)){
                        throw new BusinessRoleAlreadyExistsException("This Role is already in this team!");
                    }
                    //Adding the team to the role entity.
                    roleTeams.add(actual);
                    role.setTeams(roleTeams);
                    this.businessRoleRepository.save(role);
                    //Adding the role to the teamEntity
                    currentRoles.add(role);
                    actual.setTeamAssignedRoles(currentRoles);
                    this.teamRepository.save(actual);
                    return this.findTeams(workspaceId);
                }else{
                    System.out.println("Esse role não existe no mesmo workspace desse time.");
                }
            }
        }
        System.out.println("Time não existe nesse workspace!");
        return null;
    }

    @Override
    public BusinessRole findRoleById(Integer roleId) {
        Optional<BusinessRole> businessRole = businessRoleRepository.findById(roleId);
        return businessRole.orElseThrow(() -> new ObjectNotFoundException("Business role not found!!"));
    }

    @Override
    public Set<BusinessRole> findAllRoles(Integer workspaceId) {
        Workspace workspace = findById(workspaceId);
        return workspace.getBusinessRoles();
    }

    @Override
    public List<BusinessRole> businessRoleExistsInManyTeams(Integer workspaceId) {
        this.findById(workspaceId);
        Optional<List<BusinessRole>> optional = workspaceRepository.findRolesInMoreThanOne(workspaceId);
        return optional.orElseThrow(() -> new ObjectNotFoundException("TEST not found!!"));
    }
}
