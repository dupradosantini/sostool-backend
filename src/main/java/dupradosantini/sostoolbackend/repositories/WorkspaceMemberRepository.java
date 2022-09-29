package dupradosantini.sostoolbackend.repositories;

import dupradosantini.sostoolbackend.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Integer> {
}
