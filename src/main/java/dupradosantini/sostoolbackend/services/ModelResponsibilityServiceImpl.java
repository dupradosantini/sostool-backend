package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.ModelResponsibility;
import dupradosantini.sostoolbackend.repositories.ModelResponsibilityRepository;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import dupradosantini.sostoolbackend.services.interfaces.ModelResponsibilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ModelResponsibilityServiceImpl implements ModelResponsibilityService {

    private final ModelResponsibilityRepository modelResponsibilityRepository;

    public ModelResponsibilityServiceImpl(ModelResponsibilityRepository modelResponsibilityRepository) {
        this.modelResponsibilityRepository = modelResponsibilityRepository;
    }


    @Override
    public List<ModelResponsibility> findAll() {
        return modelResponsibilityRepository.findAll();
    }

    @Override
    public ModelResponsibility findById(Integer id) {
        Optional<ModelResponsibility> obj = modelResponsibilityRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ModelResponsibility not found!!"));
    }

    @Override
    public ModelResponsibility update(ModelResponsibility obj) {
        this.findById(obj.getId());
        return modelResponsibilityRepository.save(obj);
    }

    @Override
    public ModelResponsibility create(ModelResponsibility obj) {
        obj.setId(null);
        return modelResponsibilityRepository.save(obj);
    }
}
