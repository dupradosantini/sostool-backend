package dupradosantini.sostoolbackend.bootstrap;

import dupradosantini.sostoolbackend.domain.ModelRole;
import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.repositories.ModelRoleRepository;
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
    private final ModelRoleRepository modelRoleRepository;

    @Autowired
    public BootStrapData(WorkspaceRepository workspaceRepository, TeamRepository teamRepository, ModelRoleRepository modelRoleRepository) {
        this.workspaceRepository = workspaceRepository;
        this.teamRepository = teamRepository;
        this.modelRoleRepository = modelRoleRepository;
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

        //Engineering
        ModelRole m1 = new ModelRole("SoS Software Engineer","SoS Software Engineer description");
        ModelRole m2 = new ModelRole("SoS Software Architect","SoS Software Architect description");
        ModelRole m3 = new ModelRole("SoS System Analyst","SoS System Analyst description");
        ModelRole m4 = new ModelRole("SoS Systems Engineer","SoS Systems Engineer description");
        //Governing
        ModelRole m5 = new ModelRole("SoS Lead System Integration","SoS Lead System Integration description");
        ModelRole m6 = new ModelRole("SoS Executive","SoS Executive description");
        ModelRole m7 = new ModelRole("SoS Project Manager","SoS Project Manager description");
        ModelRole m8 = new ModelRole("SoS Sponsor","SoS Sponsor description");
        ModelRole m9 = new ModelRole("SoS Funding Authority","SoS Funding Authority description");

        modelRoleRepository.saveAll(Arrays.asList(m1,m2,m3,m4,m5,m6,m7,m8,m9));

        System.out.println("Number of workspaces: " + workspaceRepository.count());


    }
}
