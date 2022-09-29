package dupradosantini.sostoolbackend.services.interfaces;

import dupradosantini.sostoolbackend.domain.ModelResponsibility;
import dupradosantini.sostoolbackend.domain.ModelRole;

import java.util.List;

public interface ModelResponsibilityService {


    List<ModelResponsibility> findAll();

    ModelResponsibility findById(Integer id);

    ModelResponsibility update(ModelResponsibility obj);

    ModelResponsibility create(ModelResponsibility obj);

}
