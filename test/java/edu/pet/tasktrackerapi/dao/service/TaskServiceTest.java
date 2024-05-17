package edu.pet.tasktrackerapi.dao.service;

import edu.pet.tasktrackerapi.dao.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.model.Task;
import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.dao.repository.planner.TaskRepository;
import edu.pet.tasktrackerapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp(){
        taskService = new TaskService(taskRepository, modelMapper);
    }


    @Test
    void testCreateTask() {
        //given
        User user = new User();
        NewTaskRequest newTaskRequest = new NewTaskRequest();
        newTaskRequest.setTitle("Test Title");
        newTaskRequest.setDetails("Test Details");

        Task task = new Task();
        Long taskId = UUID.randomUUID();
        task.setId(taskId);

        //when
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Long result = taskService.createTask(user, newTaskRequest);

        //then
        assertEquals(taskId, result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getUsersTasksDto() {
    }

    @Test
    void getUsersTasksEntities() {
    }

    @Test
    void updateTaskIfBelongsToUser() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void deleteTaskByUUID() {
    }

    @Test
    void getNumberOfNotCompletedTasks() {
    }
}