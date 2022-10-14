package dupradosantini.sostoolbackend.bootstrap;

import dupradosantini.sostoolbackend.domain.*;
import dupradosantini.sostoolbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BootStrapData implements CommandLineRunner {

    private final WorkspaceRepository workspaceRepository;
    private final TeamRepository teamRepository;
    private final ModelRoleRepository modelRoleRepository;
    private final ModelResponsibilityRepository modelResponsibilityRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public BootStrapData(WorkspaceRepository workspaceRepository,
                         TeamRepository teamRepository,
                         ModelRoleRepository modelRoleRepository,
                         ModelResponsibilityRepository modelResponsibilityRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.workspaceRepository = workspaceRepository;
        this.teamRepository = teamRepository;
        this.modelRoleRepository = modelRoleRepository;
        this.modelResponsibilityRepository = modelResponsibilityRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
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

        var a1 = new Activity("Define SoS Objectives");
        a1.setWorkspace(w1);
        var a2 = new Activity("Define Capability Objectives");
        a2.setWorkspace(w1);
        var a3 = new Activity("Define Capability Requirements");
        a3.setWorkspace(w1);
        var a4 = new Activity("Identify Sources");
        a4.setWorkspace(w1);

        activityRepository.saveAll(Arrays.asList(a1,a2,a3,a4));

        var a5 = new Activity("Define SoS Objectives");
        a5.setWorkspace(w2);
        var a6 = new Activity("Define Capability Objectives");
        a6.setWorkspace(w2);
        var a7 = new Activity("Define Capability Requirements");
        a7.setWorkspace(w2);
        var a8 = new Activity("Identify Sources");
        a8.setWorkspace(w2);

        activityRepository.saveAll(Arrays.asList(a5,a6,a7,a8));





        ModelRole other = new ModelRole("Other", "Some role not already described.");
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

        modelRoleRepository.saveAll(Arrays.asList(other,m1,m2,m3,m4,m5,m6,m7,m8,m9));

        //Responsibility
        ModelResponsibility otherResp = new ModelResponsibility("Other");

        ModelResponsibility r1 = new ModelResponsibility("To expand or redefine existing SE processes");
        ModelResponsibility r2 = new ModelResponsibility("To understand technical-level expectations");
        ModelResponsibility r3 = new ModelResponsibility("To translate capability needs into high-level requirements");
        ModelResponsibility r4 = new ModelResponsibility("To derive the SoS requirements from the capability objectives");
        ModelResponsibility r5 = new ModelResponsibility("To understand the nature and the dynamics of the SoS");
        ModelResponsibility r6 = new ModelResponsibility("To identify new needs as the situation changes and the SoS evolves");
        ModelResponsibility r7 = new ModelResponsibility("To understand the systems involved in providing the needed SoS capabilities");
        ModelResponsibility r8 = new ModelResponsibility("To identify the stakeholders and users of SoS and systems, and understand their organizational context");
        ModelResponsibility r9 = new ModelResponsibility("To establish metrics and methods for assessing performance of the SoS capabilities");
        ModelResponsibility r10 = new ModelResponsibility("To prevent problems or develop strategies to mitigate the impact on the SoS.");
        ModelResponsibility r11 = new ModelResponsibility("To derive the SoS requirements from the capability objectives");
        ModelResponsibility r12 = new ModelResponsibility("To design the changes that will be implemented by the CS");
        ModelResponsibility r13 = new ModelResponsibility("To fund, plan, contractually enable, facilitate upgrades to the SoS");
        ModelResponsibility r14 = new ModelResponsibility("To work across systems and balance technical and nontechnical issues");
        ModelResponsibility r15 = new ModelResponsibility("To support management decisions");

        modelResponsibilityRepository.saveAll(Arrays.asList(otherResp,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15));


        AppUser appUser1 = new AppUser("Luis Eduardo","luis@email.com","senhaforte");

        userRepository.save(appUser1);

        System.out.println("Number of workspaces: " + workspaceRepository.count());


    }
}
