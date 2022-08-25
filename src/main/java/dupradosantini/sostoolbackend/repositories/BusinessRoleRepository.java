package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.BusinessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRoleRepository extends JpaRepository<BusinessRole, Integer> {
}
