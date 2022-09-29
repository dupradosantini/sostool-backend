package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findById(@PathVariable Integer id){
        AppUser obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> findAll(){
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser obj){
        AppUser newAppUser = userService.createUser(obj);
        return ResponseEntity.ok().body(newAppUser);
    }
}

