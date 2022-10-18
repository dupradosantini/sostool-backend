package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.*;
import dupradosantini.sostoolbackend.repositories.*;
import dupradosantini.sostoolbackend.services.exceptions.BusinessRoleAlreadyExistsException;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import dupradosantini.sostoolbackend.services.interfaces.WorkspaceService;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    private final BusinessRoleRepository businessRoleRepository;
    private final BusinessResponsibilityRepository businessResponsibilityRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TeamRepository teamRepository;
    private final ModelRoleServiceImpl modelRoleService;
    private final ModelResponsibilityServiceImpl modelResponsibilityService;
    private final UserServiceImpl userService;
    private final ActivityRepository activityRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository,
                                TeamRepository teamRepository,
                                BusinessRoleRepository businessRoleRepository,
                                ModelRoleServiceImpl modelRoleService,
                                ModelResponsibilityServiceImpl modelResponsibilityService,
                                BusinessResponsibilityRepository businessResponsibilityRepository, UserServiceImpl userService, ActivityRepository activityRepository) {
        this.workspaceRepository = workspaceRepository;
        this.teamRepository = teamRepository;
        this.businessRoleRepository = businessRoleRepository;
        this.modelRoleService = modelRoleService;
        this.modelResponsibilityService = modelResponsibilityService;
        this.businessResponsibilityRepository = businessResponsibilityRepository;
        this.userService = userService;
        this.activityRepository = activityRepository;
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
        var savedWorkspace = workspaceRepository.save(obj);
        var a1 = new Activity("Define SoS Objectives");
        var a2 = new Activity("Define Capability Objectives");
        var a3 = new Activity("Define Capability Requirements");
        var a4 = new Activity("Identify Sources");

        createActivity(savedWorkspace.getId(),a1);
        createActivity(savedWorkspace.getId(),a2);
        createActivity(savedWorkspace.getId(),a3);
        createActivity(savedWorkspace.getId(),a4);

        return this.findById(savedWorkspace.getId());
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

    //ROLE RELATED METHODS ***************************

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
                    System.out.println("This role does not exist in the same workspace as this team!");
                }
            }
        }
        System.out.println("Teams does not exist in this workspace!");
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
    public Team getTeamInWorkspace(Integer workspaceId, Integer teamId) {
        var team = teamRepository.findById(teamId);
        return team.orElseThrow(() -> new ObjectNotFoundException("Business role not found!!"));
    }

    @Override
    public List<BusinessRole> businessRoleExistsInManyTeams(Integer workspaceId) {
        this.findById(workspaceId);
        Optional<List<BusinessRole>> optional = workspaceRepository.findRolesInMoreThanOne(workspaceId);
        return optional.orElseThrow(() -> new ObjectNotFoundException("TEST not found!!"));
    }

    //RESPONSIBILITIES RELATED METHODS *************************

    @Override
    public BusinessResponsibility findResponsibilityById(Integer responsibilityId) {
        Optional<BusinessResponsibility> businessResponsibility = businessResponsibilityRepository.findById(responsibilityId);
        return businessResponsibility.orElseThrow(() -> new ObjectNotFoundException("Business responsibility not found!!"));
    }

    @Override
    public Set<BusinessResponsibility> findAllResponsibilities(Integer workspaceId) {
        Workspace workspace = findById(workspaceId);
        return workspace.getBusinessResponsibilities();
    }

    @Override
    public BusinessResponsibility createResponsibility(Integer workspaceId, BusinessResponsibility obj){

        obj.setId(null);
        obj.setWorkspace(this.findById(workspaceId));

        Optional<BusinessResponsibility> testIfExists = workspaceRepository.findResponsibilityWithDescriptionInWorkspace(obj.getDescription(), workspaceId);

        if(testIfExists.isPresent()){
            throw new BusinessRoleAlreadyExistsException("Business Role with this name already exists in this workspace");
        }

        //Checking if the parentRole exists.
        Integer parentResponsibilityId = obj.getParentResponsibility().getId();
        ModelResponsibility returnedParent = modelResponsibilityService.findById(parentResponsibilityId);
        //If it does, set the parent as the returned OBJ
        obj.setParentResponsibility(returnedParent);

        //Also adding the role as a son to the parent.
        Set<BusinessResponsibility> businessResponsibilitySet = returnedParent.getSonResponsibilities();
        businessResponsibilitySet.add(obj);
        returnedParent.setSonResponsibilities(businessResponsibilitySet);

        //Saving the new businessRole FIRST, then updating the modelRole its basedOn;
        BusinessResponsibility newObj = businessResponsibilityRepository.save(obj);
        modelResponsibilityService.update(returnedParent);

        return newObj;
    }

    @Override
    public Set<BusinessRole> assignResponsibilityToRole(Integer workspaceId, Integer businessRoleId, Integer responsibilityId) {
        findById(workspaceId); //checando se o workspace existe
        findRoleById(businessRoleId);
        Set<BusinessRole> businessRoleSet = this.findAllRoles(workspaceId);
        BusinessResponsibility responsibility = this.findResponsibilityById(responsibilityId);
        Set<BusinessRole> businessResponsibilityRoles = responsibility.getBusinessRoles();
        for(BusinessRole actual : businessRoleSet){
            if(actual.getId().equals(businessRoleId)){
                Set<BusinessResponsibility> currentResponsibility = actual.getRoleAssignedResponsibilities();
                if(actual.getWorkspace().equals(responsibility.getWorkspace())){
                    if(actual.getRoleAssignedResponsibilities().contains(responsibility)){
                        throw new BusinessRoleAlreadyExistsException("This Responsibility is already in this role!");
                    }
                    //Adding the role to the responsibility entity.
                    businessResponsibilityRoles.add(actual);
                    responsibility.setBusinessRoles(businessResponsibilityRoles);
                    this.businessResponsibilityRepository.save(responsibility);
                    //Adding the responsibility to the roleEntity
                    currentResponsibility.add(responsibility);
                    actual.setRoleAssignedResponsibilities(currentResponsibility);
                    this.businessRoleRepository.save(actual);
                    return this.findAllRoles(workspaceId);
                }else{
                    System.out.println("Esse responsibility não existe no mesmo workspace desse businessrole.");
                }
            }
        }
        System.out.println("BusinessRole não existe nesse workspace!");
        return null;
    }

    @Override
    public Set<BusinessResponsibility> removeResponsibilityOfRole(Integer workspaceId, Integer roleId, Integer responsibilityId) {
        findById(workspaceId);
        var role = findRoleById(roleId);
        var responsibility = findResponsibilityById(responsibilityId);
        var currentRoleSet = role.getRoleAssignedResponsibilities();
        if(currentRoleSet.contains(responsibility)){
            currentRoleSet.remove(responsibility);
            role.setRoleAssignedResponsibilities(currentRoleSet);
            businessRoleRepository.save(role);
            return role.getRoleAssignedResponsibilities();
        }else{
            System.out.println("Essa responsibility não está atribuida a esse role.");
        }
        return role.getRoleAssignedResponsibilities();
    }

    @Override
    public Set<AppUser> removeUserFromRole(Integer workspaceId, Integer roleId, AppUser userObj) {
        var workspace = findById(workspaceId);
        var role = findRoleById(roleId);
        var workspaceMembers = role.getAssignedMembers();
        for(var w:workspaceMembers){
            var appUser = w.getAppUser();
            if(appUser.getId().equals(userObj.getId()) && w.getEndDate()==null){
                w.setEndDate(java.util.Date.from(Instant.now()));
                this.userService.saveWorkspaceMember(w);
            }
        }
        return findUsersWithRole(workspaceId,roleId);
    }

    @Override
    public Set<AppUser> assignUserToRole(Integer workspaceId, Integer roleId, Integer memberId) throws ObjectNotFoundException {
        var workspace =findById(workspaceId);
        var role = findRoleById(roleId);
        var user = userService.findById(memberId);
        var userCurrentMemberSet = userService.findUserMemberSet(user,role);
        if(userCurrentMemberSet.isPresent()){
            System.out.println("Usuário ja é membro desse role.");
            return userService.findUsersWithRole(role);
        }else if(!userService.UserHasRoleInWorkspace(user, workspace)){

            var currentDate = Date.from(Instant.now());
            var newMember = new WorkspaceMember(user,role,workspace, currentDate);
            this.userService.createWorkspaceMember(newMember);
            return userService.findUsersWithRole(role);
        }
        System.out.println("Usuário possui cargo não encerrado");
        throw new ObjectNotFoundException("Usuário ja possui cargo!");
    }

    @Override
    public Set<AppUser> findUsersWithRole(Integer workspaceId, Integer roleId) {
        findById(workspaceId);
        var role = findRoleById(roleId);
        return userService.findUsersWithRole(role);
    }

    //Activities methods
    @Override
    public Activity findActivityById(Integer activityId) {
        Optional<Activity> activity = this.activityRepository.findById(activityId);
        return activity.orElseThrow(() -> new ObjectNotFoundException("Activity not found!"));
    }

    @Override
    public List<Activity> findAllActivitiesInWorkspace(Integer workspaceId) {
        return this.activityRepository.findActivitiesByWorkspaceId(workspaceId);
    }

    @Override
    public Activity createActivity(Integer workspaceId, Activity activityObj) {
        var workspace = this.findById(workspaceId);
        activityObj.setId(null);
        activityObj.setWorkspace(workspace);
        activityObj.setState(ActivityState.NOT_STARTED);
        var savedActivity =  this.activityRepository.save(activityObj);

        //Updating workspace activities list
        var currentActivities = workspace.getActivities();
        if(currentActivities == null) {
            currentActivities = new ArrayList<>();
        }
        currentActivities.add(savedActivity);
        workspace.setActivities(currentActivities);
        this.workspaceRepository.save(workspace);

        return savedActivity;
    }

    @Override
    public Activity updateActivity(Integer activityId, Activity obj) {
        var currentObj = findActivityById(activityId);
        currentObj.setDescription(obj.getDescription());
        currentObj.setState(obj.getState());
        return  activityRepository.save(currentObj);
    }

    @Override
    public Activity addMembersToActivity(Integer activityId, Set<AppUser> users) {
        var activity = this.findActivityById(activityId);
        var workspaceMembers = userService.findCurrentWorkspaceMembers(activity.getWorkspace().getId());
        Set<WorkspaceMember> filteredSet = new HashSet<>();
        for(var user:users){
            for(var wMember:workspaceMembers){
                if(wMember.getAppUser().getId().equals(user.getId())){
                    filteredSet.add(wMember);
                    var memberActivitySet = wMember.getActivities();
                    memberActivitySet.add(activity);
                    wMember.setActivities(memberActivitySet);
                }
            }
        }
        activity.setWorkspaceMember(filteredSet);
        return activityRepository.save(activity);
    }

    @Override
    public Set<AppUser> getUsersInActivity(Integer activityId) {
        var members = this.activityRepository.findMembersInActivity(activityId);
        Set<AppUser> users = new HashSet<>();
        for(var m:members){
            users.add(m.getAppUser());
        }
        return users;
    }
}
