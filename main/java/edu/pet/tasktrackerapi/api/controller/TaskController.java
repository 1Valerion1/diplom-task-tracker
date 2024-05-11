package edu.pet.tasktrackerapi.api.controller;

import edu.pet.tasktrackerapi.api.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.api.dto.TaskDto;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO document errors  http://localhost:8081/swagger-ui/index.html#/Tasks/createTask

@Controller
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Methods for task management")
public class TaskController {
    private final TaskService taskService;

    @GetMapping(produces = "application/json" , value = "/get")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@AuthenticationPrincipal User user){

        System.out.println(taskService.getUsersTasksDto(user));

        return ResponseEntity.ok(taskService.getUsersTasksDto(user));
    }

    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new task")
    public ResponseEntity<Long> createTasks(@AuthenticationPrincipal User user, @RequestBody @Valid NewTaskRequest newTaskRequest){

        Long taskId = taskService.createTask(user, newTaskRequest);

        return ResponseEntity.ok(taskId);
    }



    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing task")
    public ResponseEntity<TaskDto> updateTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskDto taskDto){

        taskService.updateTaskIfBelongsToUser(user, taskDto);

        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete task by id")
    public ResponseEntity<Long> deleteTask(@AuthenticationPrincipal User user, @PathVariable Long uuid){

        taskService.deleteTask(user, uuid);

        return ResponseEntity.ok(uuid);
    }
    /////////////////////////Сделать CRUD для SubTask тут же
    @GetMapping
    public String showQuestions(Model model) {

        return "tasks";
    }

}
