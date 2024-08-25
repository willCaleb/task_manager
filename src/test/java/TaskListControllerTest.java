import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.taskmanager.TaskmanagerApplication;
import com.will.taskmanager.controller.TaskListController;
import com.will.taskmanager.enums.EnumPriority;
import com.will.taskmanager.model.dto.TaskDto;
import com.will.taskmanager.model.dto.TaskListDto;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.service.TaskListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
public class TaskListControllerTest {

    @Mock
    @Autowired
    private TaskListController taskListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskListController).build();
    }

    @Test
    void testGetAllTask() throws Exception {
        mockMvc.perform(get("/task-list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("nome 8"))
                .andDo(print());
    }

    @Test
    void testCreateTaskList() throws Exception {

        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setName("New Task");
        taskListDto.setPriority(EnumPriority.AVERAGE);

        List<TaskDto> tasks = new ArrayList<>();

        TaskDto task1 = new TaskDto();
        task1.setTitle("Tarefa 3 editada");
        task1.setDescription("Limpar geladeira");
        task1.setCreationDate(new Date());
        task1.setPriority(EnumPriority.AVERAGE);
        tasks.add(task1);

        TaskDto task2 = new TaskDto();
        task2.setTitle("Tarefa 1");
        task2.setDescription("Limpar fogão");
        task2.setCreationDate(new Date());
        task2.setExpectedCompletionDate(new Date());
        task2.setPriority(EnumPriority.AVERAGE);
        tasks.add(task2);

        taskListDto.setTasks(tasks);

        when(taskListController.include(any(TaskListDto.class))).thenReturn(taskListDto);

        mockMvc.perform(post("/task-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testGetTaskList() throws Exception {
        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setId(15);
        taskListDto.setName("Existing Task List");

        when(taskListController.findOne(15)).thenReturn(taskListDto);

        mockMvc.perform(get("/task-list/15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Existing Task List"));

        verify(taskListController, times(1)).findOne(15);
    }

    @Test
    void testUpdateTaskList() throws Exception {
        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setId(14);
        taskListDto.setName("Updated Task List");

        String json = """
                {
                \t"id": 14,
                \t"name": "Updated Task List",
                \t"priority": "VERY_HIGH",
                \t"creationDate": "2024-08-24T18:12:17.516+00:00",
                \t"tasks": []
                }""";

        mockMvc.perform(put("/task-list/14")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(taskListController, times(1)).update(eq(14), any(TaskListDto.class));
    }


    @Test
    void testDeleteTaskList() throws Exception {
        doNothing().when(taskListController).exclude(14);

        mockMvc.perform(delete("/task-list/14"))
                .andExpect(status().isNoContent());

        verify(taskListController, times(1)).exclude(14);
    }
}
