package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
}
