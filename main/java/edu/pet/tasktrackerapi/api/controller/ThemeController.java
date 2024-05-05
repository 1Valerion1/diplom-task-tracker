package edu.pet.tasktrackerapi.api.controller;


import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.api.service.ThemesSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
@Tag(name = "Themes", description = "Методы для работы с темами ")
public class ThemeController {
     private final ThemesSevice themesSevice;
//
//    @GetMapping(produces = "application/json" , value = "/get")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(description = "Getting list of quest")
//    public ResponseEntity<List<Questions>> getTasks(@AuthenticationPrincipal Theme theme){
//
//        System.out.println(themesSevice.getThemesQuestions(theme));
//
//        return ResponseEntity.ok(themesSevice.getThemesQuestions(theme));
//    }
//
//    @PostMapping(produces = "application/json" , value = "/create")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(description = "Creating new quest")
//    public ResponseEntity<Long> createTask(@AuthenticationPrincipal Theme theme, @RequestBody @Valid Questions quest){
//
//        Long taskId = themesSevice.createQuestions(theme, quest);
//
//        return ResponseEntity.ok(taskId);
//    }
//
//
//
//    @PutMapping(value = "/update", produces = "application/json")
//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(description = "Updating existing quest")
//    public ResponseEntity<?> updateTask(@RequestBody @Valid Questions quest){
//
//        themesSevice.updateQuestions(quest);
//
//        return ResponseEntity.ok(quest);
//    }
//
    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete quest by id")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal Theme theme, @PathVariable Long uuid){

        themesSevice.deleteTheme(uuid);

        return ResponseEntity.ok(uuid);
    }
}
