package edu.pet.tasktrackerapi.api.controller;


import edu.pet.tasktrackerapi.api.model.Plan;
import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.api.service.PlansService;
import edu.pet.tasktrackerapi.api.service.ThemesSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
@Tag(name = "Themes", description = "Методы для работы с темами ")
public class ThemeController {
     private final ThemesSevice themesSevice;
    @GetMapping(produces = "application/json" , value = "/getALL")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of themes")
    public ResponseEntity<List<Theme>> getALLTheme(){

        System.out.println(themesSevice.getAllThemes());

        return ResponseEntity.ok(themesSevice.getAllThemes());
    }
    @GetMapping(produces = "application/json" , value = "/getOne")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting theme")
    public ResponseEntity<Theme> getThemeId(@RequestParam Long id){

        System.out.println(themesSevice.getThemesId(id));

        return ResponseEntity.ok(themesSevice.getThemesId(id));
    }
    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new quest")
    public ResponseEntity<Long> createTheme( @RequestBody @Valid Theme theme){

        Long themeId = themesSevice.createTheme(theme);

        return ResponseEntity.ok(themeId);
    }



    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing quest")
    public ResponseEntity<?> updateTheme(@RequestBody @Valid Theme theme){

        themesSevice.updateTheme(theme);

        return ResponseEntity.ok(theme);
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete theme by id")
    public ResponseEntity<?> deleteTask(@PathVariable Long uuid){

        themesSevice.deleteTheme(uuid);

        return ResponseEntity.ok(uuid);
    }
}
