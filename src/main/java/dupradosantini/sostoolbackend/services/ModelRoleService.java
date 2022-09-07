package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.ModelRole;

import java.util.List;

public interface ModelRoleService {

    List<ModelRole> findAll();

    ModelRole findById(Integer id);

    ModelRole update(ModelRole obj);

    ModelRole create(ModelRole obj);
}
