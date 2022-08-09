package dupradosantini.sostoolbackend.bootstrap;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.repositories.TeamRepository;
import dupradosantini.sostoolbackend.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BootStrapData implements CommandLineRunner {

    private final WorkspaceRepository workspaceRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public BootStrapData(WorkspaceRepository workspaceRepository, TeamRepository teamRepository) {
        this.workspaceRepository = workspaceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args){
        Workspace w1 =  new Workspace("Project 1", "Description of project 1");
        Workspace w2 = new Workspace("Project 2","Description of project 2");

        workspaceRepository.saveAll(Arrays.asList(w1,w2));

        Team t1w1 = new Team("Governing Body (P1)", w1);
        Team t2w1 = new Team("SoSEngineering (P1)", w1);

        Team t1w2 = new Team("Governing Body (P2)", w2);
        Team t2w2 = new Team("SoSEngineering (P2)", w2);



        teamRepository.saveAll(Arrays.asList(t1w1,t1w2,t2w1,t2w2));


        System.out.println("Number of workspaces: " + workspaceRepository.count());


    }
}
