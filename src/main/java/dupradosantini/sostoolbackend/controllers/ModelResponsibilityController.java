package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.ModelResponsibility;
import dupradosantini.sostoolbackend.services.interfaces.ModelResponsibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/model-responsibility")
public class ModelResponsibilityController {

    private final ModelResponsibilityService modelResponsibilityService;

    @Autowired
    public ModelResponsibilityController(ModelResponsibilityService modelResponsibilityService) {
        this.modelResponsibilityService = modelResponsibilityService;
    }

    //FIND ALL METHOD //TODO - Maybe DTOs for Swagger consistency
    @Operation(summary = "Fetches all the ModelResposibilities available!")
    @ApiResponse(responseCode = "200", description = "List of all ModelResponsibilities retrieved")
    @GetMapping
    public ResponseEntity<List<ModelResponsibility>> findAll() {
        List<ModelResponsibility> modelResponsibilityList = modelResponsibilityService.findAll();
        return ResponseEntity.ok().body(modelResponsibilityList);

    }

    //CREATE MODEL ROLE METHOD
    @PostMapping
    public ResponseEntity<ModelResponsibility> create(@RequestBody ModelResponsibility obj) {
        ModelResponsibility newModelResponsibility = modelResponsibilityService.create(obj);
        return ResponseEntity.ok().body(newModelResponsibility);
    }

    @PostMapping("/edit")
    public ResponseEntity<ModelResponsibility> update(@RequestBody ModelResponsibility obj){
        ModelResponsibility updatedResp = modelResponsibilityService.update(obj);
        return ResponseEntity.ok().body(updatedResp);
    }

}
