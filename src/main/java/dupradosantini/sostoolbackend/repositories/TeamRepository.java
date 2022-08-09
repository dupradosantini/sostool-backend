package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
