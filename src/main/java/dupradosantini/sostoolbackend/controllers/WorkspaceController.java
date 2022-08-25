package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.services.WorkspaceService;
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

    //FIND ALL METHOD //TODO - PAGING and Maybe DTOs for Swagger consistency
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

    //CREATE ROLE GIVEN A WORKSPACE.
    @PostMapping("/{workspaceId}/businessroles")
    public ResponseEntity<BusinessRole> createRoleInWorkspace(@PathVariable Integer workspaceId, @RequestBody BusinessRole obj){
        BusinessRole newBusinessRole = workspaceService.createRole(workspaceId,obj);
        return ResponseEntity.ok().body(newBusinessRole);
    }

    //ASSIGN ROLE TO TEAM.
    @PutMapping("/{workspaceId}/teams/{teamId}/roles/{roleId}")
    public ResponseEntity<Team> assignRoleToTeam(@PathVariable Integer workspaceId, @PathVariable Integer teamId, @PathVariable Integer roleId){
        Team updatedTeam =  workspaceService.assignRoleToTeam(workspaceId,teamId,roleId);
        return ResponseEntity.ok().body(updatedTeam);
    }
}
