package dupradosantini.sostoolbackend.controllers;


import dupradosantini.sostoolbackend.domain.Activity;
import dupradosantini.sostoolbackend.services.interfaces.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Controller
@RequestMapping("/activities")
public class ActivityController {

    private final WorkspaceService workspaceService;

    public ActivityController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Integer activityId, @RequestBody Activity obj){
        var updatedActivity = workspaceService.updateActivity(activityId, obj);
        return ResponseEntity.ok().body(updatedActivity);
    }
}
