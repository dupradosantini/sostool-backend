package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.ModelRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRoleRepository extends JpaRepository<ModelRole, Integer> {
}
