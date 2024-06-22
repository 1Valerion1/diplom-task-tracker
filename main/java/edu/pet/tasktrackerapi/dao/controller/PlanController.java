package edu.pet.tasktrackerapi.dao.controller;

import edu.pet.tasktrackerapi.model.Plan;
import edu.pet.tasktrackerapi.service.PlansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
@Tag(name = "Plans", description = "Methods for plans management")
public class PlanController {

    private final PlansService plansService;

    @GetMapping(produces = "application/json", value = "/getOne")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Getting plan")
    @ResponseBody
    public Plan getPlan(@RequestParam Long id) {

        System.out.println(plansService.getPlanId(id));

        return plansService.getPlanId(id);
    }
    @PreAuthorize("hasRole('ADMIN')") //@Secured
    @PostMapping(produces = "application/json", value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new plan")
    @ResponseBody
    public Long createPlan(@RequestBody @Valid Plan plan) {

        Long planId = plansService.createPlan(plan);

        return planId;
    }


    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing plan")
    @ResponseBody
    public Plan updatePlan(@RequestBody @Valid Plan plan) {

        plansService.updatePlan(plan);

        return plan;
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete plan by id")
    @ResponseBody
    public Long deleteTask(@PathVariable Long uuid) {

        plansService.deletePlan(uuid);

        return uuid;
    }

    @GetMapping
    public String showQuestions(Model model) {
        model.addAttribute("plans", plansService.getPlanId(2l));
        return "plans";
    }


}
