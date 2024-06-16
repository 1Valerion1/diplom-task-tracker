package edu.pet.tasktrackerapi.dao.repository.planner;

import edu.pet.tasktrackerapi.model.Plan;
import edu.pet.tasktrackerapi.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Questions> getPlanByTheme_Id(Long id);
    void deletePlanById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Plan p SET p.developer = :developer WHERE p.id = :id")
    void updateDev(@Param("id") Long id, @Param("developer") String developer);

}
