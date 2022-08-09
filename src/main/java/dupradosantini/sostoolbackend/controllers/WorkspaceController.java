package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.services.WorkspaceService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Workspace> findById(@PathVariable Integer id){
        Workspace obj = this.workspaceService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> findAll(){
        List<Workspace> objList = this.workspaceService.findAll();
        return ResponseEntity.ok().body(objList);
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<Set<Team>> findTeamsInWorkspace(@PathVariable Integer id){
        Set<Team> objSet = this.workspaceService.findTeams(id);
        return ResponseEntity.ok().body(objSet);
    }
}
