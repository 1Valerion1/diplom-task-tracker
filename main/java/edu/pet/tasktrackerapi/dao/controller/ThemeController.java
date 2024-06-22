package edu.pet.tasktrackerapi.dao.controller;


import edu.pet.tasktrackerapi.model.Theme;
import edu.pet.tasktrackerapi.service.ThemesSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
@Tag(name = "Themes", description = "Методы для работы с темами ")
public class ThemeController {
     private final ThemesSevice themesSevice;
    @GetMapping(produces = "application/json" , value = "/getALL")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting list of themes")
    @ResponseBody
    public List<Theme> getALLTheme(){

        System.out.println(themesSevice.getAllThemes());

        return themesSevice.getAllThemes();
    }
    @GetMapping(produces = "application/json" , value = "/getOne")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting theme")
    @ResponseBody
    public Theme getThemeId(@RequestParam Long id){

        System.out.println(themesSevice.getThemesId(id));

        return themesSevice.getThemesId(id);
    }
    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new quest")
    @ResponseBody
    public Long createTheme( @RequestBody @Valid Theme theme){

        Long themeId = themesSevice.createTheme(theme);

        return themeId;
    }



    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing quest")
    @ResponseBody
    public Theme updateTheme(@RequestBody @Valid Theme theme){

        themesSevice.updateTheme(theme);

        return theme;
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete theme by id")
    @ResponseBody
    public Long deleteTask(@PathVariable Long uuid){

        themesSevice.deleteTheme(uuid);

        return uuid;
    }
}
