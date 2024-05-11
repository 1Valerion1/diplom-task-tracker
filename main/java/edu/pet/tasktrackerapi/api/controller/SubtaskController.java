package edu.pet.tasktrackerapi.api.controller;


import edu.pet.tasktrackerapi.api.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.api.dto.SubtaskDTO;
import edu.pet.tasktrackerapi.api.dto.TaskDto;
import edu.pet.tasktrackerapi.api.model.Subtask;
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
    @GetMapping(produces = "application/json" , value = "/getAll")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting all list of subtasks")
    public ResponseEntity<List<Subtask>> getSubtasksAll(){

        System.out.println(subtaskService.getSubtaskAll());

        return ResponseEntity.ok(subtaskService.getSubtaskAll());
    }

    @GetMapping(produces = "application/json" , value = "/getOne")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of subtasks")
    public ResponseEntity<List<SubtaskDTO>> getSubtasks(Task task){

        System.out.println(subtaskService.getSubtask(task));

        return ResponseEntity.ok(subtaskService.getSubtask(task));
    }

    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new task")
    public ResponseEntity<Long> createSubtasks(@AuthenticationPrincipal Task task, @RequestBody @Valid NewTaskRequest newTaskRequest){

        Long taskId = subtaskService.createSubtask(task, newTaskRequest);

        return ResponseEntity.ok(taskId);
    }


    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing subtask")
    public ResponseEntity<SubtaskDTO> updateSubtask(@AuthenticationPrincipal Task task, @RequestBody @Valid SubtaskDTO subtaskDTO){

        subtaskService.updateTaskIfBelongsToUser(task, subtaskDTO);

        return ResponseEntity.ok(subtaskDTO);
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete task by id")
    public ResponseEntity<Long> deleteSubtask(@AuthenticationPrincipal Task task, @PathVariable Long uuid){

        subtaskService.deleteSubtask(task, uuid);

        return ResponseEntity.ok(uuid);
    }


}
