package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.ModelRole;
import dupradosantini.sostoolbackend.services.interfaces.ModelRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/modelrole")
public class ModelRoleController {

    private final ModelRoleService modelRoleService;

    @Autowired
    public ModelRoleController(ModelRoleService modelRoleService) {
        this.modelRoleService = modelRoleService;
    }

    //FIND ALL METHOD //TODO - Maybe DTOs for Swagger consistency
    @Operation(summary = "Fetches all the ModelRoles available!")
    @ApiResponse(responseCode = "200", description = "List of all ModelRoles retrieved")
    @GetMapping
    public ResponseEntity<List<ModelRole>> findAll() {
        List<ModelRole> modelRoleList = this.modelRoleService.findAll();
        return ResponseEntity.ok().body(modelRoleList);

    }

    //CREATE MODEL ROLE METHOD
    @PostMapping
    public ResponseEntity<ModelRole> create(@RequestBody ModelRole obj) {
        ModelRole newModelRole = modelRoleService.create(obj);
        return ResponseEntity.ok().body(newModelRole);
    }

}
