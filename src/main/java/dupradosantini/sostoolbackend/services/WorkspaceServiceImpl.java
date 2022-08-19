package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.repositories.WorkspaceRepository;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService{

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace findById(Integer id) {
        log.debug("Im in the workspace service, findById Method");
        Optional<Workspace> obj = workspaceRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Workspace not found!!"));
    }

    @Override
    public Workspace create(Workspace obj) {
        obj.setId(null);
        obj.setTeams(null);
        return workspaceRepository.save(obj);
    }

    @Override
    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        workspaceRepository.deleteById(id);
    }

    @Override
    public Set<Team> findTeams(Integer id) {
        return workspaceRepository.findTeamsOfWorkspace(id);
    }
}
