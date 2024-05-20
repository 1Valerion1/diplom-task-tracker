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
    public ResponseEntity<Plan> getPlan(@RequestParam Long id) {

        System.out.println(plansService.getPlanId(id));

        return ResponseEntity.ok(plansService.getPlanId(id));
    }
    @PreAuthorize("hasRole('ADMIN')") //@Secured
    @PostMapping(produces = "application/json", value = "/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Creating new plan")
    public ResponseEntity<Long> createPlan(@RequestBody @Valid Plan plan) {

        Long planId = plansService.createPlan(plan);

        return ResponseEntity.ok(planId);
    }


    @PutMapping(value = "/update", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Updating existing plan")
    public ResponseEntity<?> updatePlan(@RequestBody @Valid Plan plan) {

        plansService.updatePlan(plan);

        return ResponseEntity.ok(plan);
    }

    @DeleteMapping(path = "/delete/{uuid}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Delete plan by id")
    public ResponseEntity<?> deleteTask(@PathVariable Long uuid) {

        plansService.deletePlan(uuid);

        return ResponseEntity.ok(uuid);
    }

    @GetMapping
    public String showQuestions(Model model) {
        model.addAttribute("plans", plansService.getPlanId(2l));
        return "plans";
    }


}
