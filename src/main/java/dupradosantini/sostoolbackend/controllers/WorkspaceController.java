package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.*;
import dupradosantini.sostoolbackend.services.interfaces.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    // FIND BY ID METHOD
    @Operation(summary = "Fetches a single Workspace information, by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Workspace information retrieved succesfully",
                    content = {
                        @Content(mediaType = "application/json",
                        schema = @Schema(description = "Workspace Schema", implementation = Workspace.class))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Workspace was not found in the database",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> findById(@PathVariable Integer id){
        Workspace obj = this.workspaceService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    //FIND ALL METHOD
    @Operation(summary = "Fetches all the Workspaces available!")
    @ApiResponse(responseCode = "200", description = "List of all workspaces retrieved")
    @GetMapping
    public ResponseEntity<List<Workspace>> findAll(){
        List<Workspace> objList = this.workspaceService.findAll();
        return ResponseEntity.ok().body(objList);
    }

    //FIND ALL TEAMS IN WORKSPACE METHOD
    @Operation(summary = "Fetches all the teams of a given workspace (by its ID)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Workspace's teams information retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Workspace was not found in the database",
                    content = @Content())
    })
    @GetMapping("/{id}/teams")
    public ResponseEntity<Set<Team>> findTeamsInWorkspace(@PathVariable Integer id){
        Set<Team> objSet = this.workspaceService.findTeams(id);
        return ResponseEntity.ok().body(objSet);
    }

    // CREATE WORKSPACE
    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace obj){
        Workspace newWorkspace = workspaceService.create(obj);
        return ResponseEntity.ok().body(newWorkspace);
    }

    //DELETE WORKSPACE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Integer id){
        workspaceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //CREATE TEAM IN A WORKSPACE
    @PostMapping("/{id}/teams")
    public ResponseEntity<Team> createTeamInWorkspace(@PathVariable Integer id, @RequestBody Team obj){
        Team newTeam = workspaceService.createTeam(id, obj);
        return ResponseEntity.ok().body(newTeam);
    }

    //DELETE TEAM IN A WORKSPACE
    @DeleteMapping("/{workspaceId}/teams/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer workspaceId, @PathVariable Integer teamId){
        workspaceService.deleteTeam(workspaceId,teamId);
        return ResponseEntity.noContent().build();
    }


    //ROLE MANAGEMENT ********************************

    //GET BUSINESSROLES GIVEN A WORKSPACE
    @GetMapping("/{workspaceId}/businessroles")
    public ResponseEntity<Set<BusinessRole>> getRolesInWorkspace(@PathVariable Integer workspaceId){
        Set<BusinessRole> businessRoleSet = workspaceService.findAllRoles(workspaceId);
        return ResponseEntity.ok().body(businessRoleSet);
    }

    //CREATE ROLE GIVEN A WORKSPACE.
    @PostMapping("/{workspaceId}/businessroles")
    public ResponseEntity<BusinessRole> createRoleInWorkspace(@PathVariable Integer workspaceId, @RequestBody BusinessRole obj){
        BusinessRole newBusinessRole = workspaceService.createRole(workspaceId,obj);
        return ResponseEntity.ok().body(newBusinessRole);
    }

    //ASSIGN ROLE TO TEAM.
    @PutMapping("/{workspaceId}/teams/{teamId}/roles/{roleId}")
    public ResponseEntity<Set<Team>> assignRoleToTeam(@PathVariable Integer workspaceId, @PathVariable Integer teamId, @PathVariable Integer roleId){
        Set<Team> updatedTeamSet =  workspaceService.assignRoleToTeam(workspaceId,teamId,roleId);
        return ResponseEntity.ok().body(updatedTeamSet);
    }

    @GetMapping("/{workspaceId}/teams/{teamId}/roles")
    public ResponseEntity<Set<BusinessRole>> getRolesInTeam(@PathVariable Integer workspaceId, @PathVariable Integer teamId){
        var team = workspaceService.getTeamInWorkspace(workspaceId,teamId);
        return ResponseEntity.ok().body(team.getTeamAssignedRoles());
    }

    @GetMapping("/{workspaceId}/roles-in-many-teams")
    public ResponseEntity<List<BusinessRole>> getRolesInManyTeams(@PathVariable Integer workspaceId){
        List<BusinessRole> testList = workspaceService.businessRoleExistsInManyTeams(workspaceId);
        return ResponseEntity.ok().body(testList);
    }

    //RESPONSIBILITY MANAGEMENT ********************************

    //GET BUSINESS-RESPONSIBILITIES GIVEN A WORKSPACE
    @GetMapping("/{workspaceId}/business-responsibilities")
    public ResponseEntity<Set<BusinessResponsibility>> getResponsibilitiesInWorkspace(@PathVariable Integer workspaceId){
        Set<BusinessResponsibility> businessResponsibilitySet = workspaceService.findAllResponsibilities(workspaceId);
        return ResponseEntity.ok().body(businessResponsibilitySet);
    }

    //CREATE RESPONSIBILITY GIVEN A WORKSPACE.
    @PostMapping("/{workspaceId}/business-responsibilities")
    public ResponseEntity<BusinessResponsibility> createResponsibilityInWorkspace(@PathVariable Integer workspaceId, @RequestBody BusinessResponsibility obj){
        BusinessResponsibility newBusinessResponsibility = workspaceService.createResponsibility(workspaceId,obj);
        return ResponseEntity.ok().body(newBusinessResponsibility);
    }

    //ASSIGN RESPONSIBILITY TO ROLE.
    @PutMapping("/{workspaceId}/businessroles/{roleId}/responsibilities/{responsibilityId}")
    public ResponseEntity<Set<BusinessRole>> assignResponsibilityToRole(
            @PathVariable Integer workspaceId,
            @PathVariable Integer roleId,
            @PathVariable Integer responsibilityId){
        Set<BusinessRole> updatedRoleSet =  workspaceService.assignResponsibilityToRole(workspaceId,roleId,responsibilityId);
        return ResponseEntity.ok().body(updatedRoleSet);
    }

    //REMOVE RESPONSIBILITY FROM ROLE
    @PutMapping("/{workspaceId}/businessroles/{roleId}/responsibilities/{responsibilityId}/remove")
    public ResponseEntity<Set<BusinessResponsibility>> removeResponsibilityFromRole(
            @PathVariable Integer workspaceId,
            @PathVariable Integer roleId,
            @PathVariable Integer responsibilityId){
        Set<BusinessResponsibility> updatedRespSet =  workspaceService.removeResponsibilityOfRole(workspaceId,roleId,responsibilityId);
        return ResponseEntity.ok().body(updatedRespSet);
    }

    //Assign member to role
    @PutMapping("/{workspaceId}/businessroles/{roleId}/member/{memberId}")
    public ResponseEntity<Set<AppUser>> assignUserToRole(
            @PathVariable Integer workspaceId,
            @PathVariable Integer roleId,
            @PathVariable Integer memberId){
        Set<AppUser> userSet = workspaceService.assignUserToRole(workspaceId,roleId,memberId);
        return ResponseEntity.ok().body(userSet);
    }

    //Remove member from role
    @PutMapping("/{workspaceId}/businessroles/{roleId}/member-remove")
    public ResponseEntity<Set<AppUser>> removeUserFromRole(
            @PathVariable Integer workspaceId,
            @PathVariable Integer roleId,
            @RequestBody AppUser userObj){
        Set<AppUser> userSet = workspaceService.removeUserFromRole(workspaceId,roleId,userObj);
        return ResponseEntity.ok().body(userSet);
    }

    @GetMapping("/{workspaceId}/businessroles/{roleId}/members")
    public ResponseEntity<Set<AppUser>> getUsersInThisRole(
            @PathVariable Integer workspaceId,
            @PathVariable Integer roleId){
        Set<AppUser> userSet = workspaceService.findUsersWithRole(workspaceId,roleId);
        return ResponseEntity.ok().body(userSet);
    }

    //Activity related endpoints

    @GetMapping("/{workspaceId}/activities")
    public ResponseEntity<List<Activity>> getActivitiesInWorkspace(@PathVariable Integer workspaceId){
        var activities = this.workspaceService.findAllActivitiesInWorkspace(workspaceId);
        return ResponseEntity.ok().body(activities);
    }

    @PostMapping("/{workspaceId}/activities")
    public ResponseEntity<Activity> createNewActivity(
            @PathVariable Integer workspaceId,
            @RequestBody Activity activityObj){
        var createdActivity = this.workspaceService.createActivity(workspaceId,activityObj);
        return ResponseEntity.ok().body(createdActivity);
    }
}
