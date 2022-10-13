package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceImplTest {

    private final Integer WORKSPACE_ID = 1;

    WorkspaceServiceImpl workspaceService;

    @Mock
    WorkspaceRepository workspaceRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    BusinessRoleRepository businessRoleRepository;

    @Mock
    BusinessResponsibilityRepository businessResponsibilityRepository;

    @Mock
    ModelRoleServiceImpl modelRoleService;

    @Mock
    ModelResponsibilityServiceImpl modelResponsibilityService;

    @Mock
    UserServiceImpl userService;

    @Mock
    ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(workspaceRepository);
        MockitoAnnotations.openMocks(teamRepository);
        MockitoAnnotations.openMocks(businessRoleRepository);
        MockitoAnnotations.openMocks(modelRoleService);
        MockitoAnnotations.openMocks(modelResponsibilityService);
        MockitoAnnotations.openMocks(activityRepository);
        workspaceService = new WorkspaceServiceImpl(
                workspaceRepository,
                teamRepository,
                businessRoleRepository,
                modelRoleService,
                modelResponsibilityService,
                businessResponsibilityRepository,
                userService,
                activityRepository);
    }

    @Test
    void findById() {
        Workspace project = new Workspace();
        project.setId(WORKSPACE_ID);

        Optional<Workspace> workspaceOptional = Optional.of(project);

        when(workspaceRepository.findById(anyInt())).thenReturn(workspaceOptional);

        Workspace returnedWorkspace = workspaceService.findById(WORKSPACE_ID);

        assertEquals(project, returnedWorkspace,"The returned workspace was not expected");
        verify(workspaceRepository,times(1)).findById(anyInt());
    }

    @Test
    void findAll() {
        List<Workspace> workspaceList = new ArrayList<>();

        when(workspaceRepository.findAll()).thenReturn(workspaceList);

        List<Workspace> listReturned = workspaceRepository.findAll();
        assertNotNull(listReturned, "Null page returned, expected empty list.");
        assertTrue(listReturned.isEmpty(),"List should be empty");
        verify(workspaceRepository,times(1)).findAll();
    }

    @Test
    void findTeams() {

        Set<Team> teamSet = new HashSet<>();

        when(workspaceRepository.findTeamsOfWorkspace(anyInt())).thenReturn(teamSet);

        Set<Team> returnedTeam = workspaceRepository.findTeamsOfWorkspace(WORKSPACE_ID);

        verify(workspaceRepository, times(1)).findTeamsOfWorkspace(anyInt());

        assertEquals(teamSet,returnedTeam,"Sets should be equal");

    }
}