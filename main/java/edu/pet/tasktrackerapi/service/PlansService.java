package edu.pet.tasktrackerapi.service;

import edu.pet.tasktrackerapi.model.Plan;
import edu.pet.tasktrackerapi.dao.exception.NotFoundException;
import edu.pet.tasktrackerapi.dao.repository.planner.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlansService {
    private final PlanRepository planRepository;
    public Long createPlan(Plan plan) {
        Plan plans = Plan
                .builder()
                .developer(plan.getDeveloper())
                .build();

        return planRepository.save(plans).getId();
    }

    public Plan getPlanId(Long id) {
        Optional<Plan> planeOptional = planRepository.findById(id);
        if (planeOptional.isPresent()) {
            return planeOptional.get();
        } else {
            throw new NotFoundException();
        }
    }

    public void updatePlan(Plan plan) {
        planRepository.updateDev(plan.getId(), plan.getDeveloper());
    }

    @Transactional
    public void deletePlan (Long id) {
        planRepository.deletePlanById(id);
    }

}
