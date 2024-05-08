package edu.pet.tasktrackerapi.api.controller;


import edu.pet.tasktrackerapi.api.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.api.dto.TaskDto;
import edu.pet.tasktrackerapi.api.model.Task;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.api.service.SubtaskService;
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

@Controller
@RequestMapping("/api/v1/subtasks")
@RequiredArgsConstructor
@Tag(name = "Subtasks", description = "Methods for subtask management")
public class SubtaskController {

    private final SubtaskService subtaskService;
//
//    @GetMapping(produces = "application/json" , value = "/getAll")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(description = "Getting list of tasks")
//    public ResponseEntity<List<TaskDto>> getSubtasks(){
//
//        System.out.println(subtaskService.getUsersTasksDto());
//
//        return ResponseEntity.ok(subtaskService.getUsersTasksDto());
//    }

    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new task")
    public ResponseEntity<Long> createTasks(@AuthenticationPrincipal Task task, @RequestBody @Valid NewTaskRequest newTaskRequest){

        Long taskId = subtaskService.createSubtask(task, newTaskRequest);

        return ResponseEntity.ok(taskId);
    }



//    @PutMapping(value = "/update", produces = "application/json")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(description = "Updating existing task")
//    public ResponseEntity<TaskDto> updateTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskDto taskDto){
//
//        subtaskService.updateTaskIfBelongsToUser(user, taskDto);
//
//        return ResponseEntity.ok(taskDto);
//    }
//
    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete task by id")
    public ResponseEntity<Long> deleteTask(@AuthenticationPrincipal Task task, @PathVariable Long uuid){

        subtaskService.deleteSubtask(task, uuid);

        return ResponseEntity.ok(uuid);
    }


}
