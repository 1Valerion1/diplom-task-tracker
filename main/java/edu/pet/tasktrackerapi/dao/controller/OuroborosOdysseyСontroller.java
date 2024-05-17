package edu.pet.tasktrackerapi.dao.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/ouroboros")
@RequiredArgsConstructor
@Tag(name = "Главная страница", description = "Методы для работы со страницей, просто выдача странички ")
public class OuroborosOdysseyСontroller {
    @GetMapping
    public String showUser(Model model) {
        return "ouroboros";
    }
}
