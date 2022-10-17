package dupradosantini.sostoolbackend.controllers;


import dupradosantini.sostoolbackend.domain.Activity;
import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.services.interfaces.WorkspaceService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @PutMapping("/{activityId}/add-members")
    public ResponseEntity<Activity> addMembersToActivity(@PathVariable Integer activityId, @RequestBody Set<AppUser> users){
        Activity updatedActivity = this.workspaceService.addMembersToActivity(activityId,users);
        return ResponseEntity.ok().body(updatedActivity);
    }

    @GetMapping("/{activityId}/users")
    public ResponseEntity<Set<AppUser>> getActivityUsers(@PathVariable Integer activityId){
        Set<AppUser> users = this.workspaceService.getUsersInActivity(activityId);
        return ResponseEntity.ok().body(users);
    }
}
