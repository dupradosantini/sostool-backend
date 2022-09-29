package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.ModelRole;
import dupradosantini.sostoolbackend.repositories.ModelRoleRepository;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import dupradosantini.sostoolbackend.services.interfaces.ModelRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ModelRoleServiceImpl implements ModelRoleService {

    private final ModelRoleRepository modelRoleRepository;

    @Autowired
    public ModelRoleServiceImpl(ModelRoleRepository modelRoleRepository) {
        this.modelRoleRepository = modelRoleRepository;
    }

    @Override
    public List<ModelRole> findAll() {
        return modelRoleRepository.findAll();
    }

    @Override
    public ModelRole findById(Integer id) {
        Optional<ModelRole> obj = modelRoleRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ModelRole not found!!"));
    }

    @Override
    public ModelRole update(ModelRole obj) {
        this.findById(obj.getId());
        return modelRoleRepository.save(obj);
    }

    @Override
    public ModelRole create(ModelRole obj) {
        obj.setId(null);
        return modelRoleRepository.save(obj);
    }
}
