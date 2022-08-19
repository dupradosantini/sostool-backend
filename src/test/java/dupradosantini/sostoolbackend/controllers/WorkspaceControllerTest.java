package dupradosantini.sostoolbackend.controllers;

import dupradosantini.sostoolbackend.domain.Team;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.services.WorkspaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkspaceControllerTest {

    @Mock
    WorkspaceServiceImpl workspaceService;

    WorkspaceController workspaceController;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        workspaceController = new WorkspaceController(workspaceService);
        mockMvc = MockMvcBuilders.standaloneSetup(workspaceController).build();
    }

    @Test
    void findById() throws Exception{
        Workspace project = new Workspace();
        project.setId(1);

        when(workspaceService.findById(anyInt())).thenReturn(project);

        mockMvc.perform(get("/workspace/1"))
                .andExpect(status().isOk());
    }

    @Test
    void findAll()  throws Exception{
        List<Workspace> workspaceList = new ArrayList<>();

        when(workspaceService.findAll()).thenReturn(workspaceList);

        mockMvc.perform(get("/workspace"))
                .andExpect(status().isOk());
    }

    @Test
    void findTeamsInWorkspace() throws Exception{
        Workspace project = new Workspace();
        project.setId(1);

        Set<Team> teamSet = new HashSet<>();

        when(workspaceService.findTeams(anyInt())).thenReturn(teamSet);

        mockMvc.perform(get("/workspace/1/teams"))
                .andExpect(status().isOk());
    }
}