package edu.pet.tasktrackerapi.api.controller;


import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.api.service.QuestionsService;
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
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@Tag(name = "Questions", description = "Methods for questions management")
public class QuestionsController {
    private final QuestionsService questionsService;

    @GetMapping(produces = "application/json" , value = "/get")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of quest")
    public ResponseEntity<List<Questions>> getQuestions(@AuthenticationPrincipal Theme theme){

        System.out.println(questionsService.getThemesQuestions(theme));

        return ResponseEntity.ok(questionsService.getThemesQuestions(theme));
    }

    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new quest")
    public ResponseEntity<Long> createTask(@AuthenticationPrincipal Theme theme, @RequestBody @Valid Questions quest){

        Long taskId = questionsService.createQuestions(theme, quest);

        return ResponseEntity.ok(taskId);
    }



    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing quest")
    public ResponseEntity<?> updateTask(@RequestBody @Valid Questions quest){

        questionsService.updateQuestions(quest);

        return ResponseEntity.ok(quest);
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete quest by id")
    public ResponseEntity<Long> deleteTask(@AuthenticationPrincipal Theme theme, @PathVariable Long uuid){

        questionsService.deleteQuest(theme, uuid);

        return ResponseEntity.ok(uuid);
    }

    @GetMapping
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionsService.getAllQuestions());
        return "questions";
    }

    @GetMapping(produces = "application/json" , value = "/getAll")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of quest")
    public ResponseEntity<List<Questions>> getAllQuest(){

        //  System.out.println(questionsService.getAllQuestions());

        return ResponseEntity.ok(questionsService.getAllQuestions());
    }


}
