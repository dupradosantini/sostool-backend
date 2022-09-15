package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.ModelResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelResponsibilityRepository extends JpaRepository<ModelResponsibility, Integer> {
}
