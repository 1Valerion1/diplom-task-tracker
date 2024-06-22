package edu.pet.tasktrackerapi.dao.controller;


import edu.pet.tasktrackerapi.model.Questions;
import edu.pet.tasktrackerapi.model.Theme;
import edu.pet.tasktrackerapi.service.QuestionsService;
import edu.pet.tasktrackerapi.service.ThemesSevice;
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
    private final ThemesSevice themesSevice;


    @GetMapping(produces = "application/json" , value = "/getAll")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting a list of questions")
    @ResponseBody
    public List<Questions> getAllQuest(){

        //  System.out.println(questionsService.getAllQuestions());

        return questionsService.getAllQuestions();
    }
    @GetMapping(produces = "application/json" , value = "/getQuestionsThemes")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting questions on a theme")
    @ResponseBody
    public List<Questions> getQuestionsThemes(@RequestParam Long themeId){
        System.out.println(questionsService.getThemeQuestions(themeId));
        return questionsService.getThemeQuestions(themeId);
    }

    @PostMapping(produces = "application/json" , value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new quest")
    @ResponseBody
    public Long createQuest(@RequestBody @Valid Questions quest){
        List<Theme> themes =  themesSevice.findByName(quest.getNameThemes());
//        if(themes.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
        Theme theme = themes.get(0);
        quest.setTheme(theme);

        Long questId = questionsService.createQuestions(quest);

        return questId;
    }

    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing quest")
    @ResponseBody
    public Questions updateQuest(@RequestBody @Valid Questions quest){

        questionsService.updateQuestions(quest);

        return quest;
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    // @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete quest by id")
    @ResponseBody
    public Long deleteQuest(@AuthenticationPrincipal Theme theme, @PathVariable Long uuid){

        questionsService.deleteQuest(theme, uuid);

        return uuid;
    }

    @GetMapping
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionsService.getAllQuestions());
        return "questions";
    }
}
