package edu.pet.tasktrackerapi.repository.planner;

import edu.pet.tasktrackerapi.api.model.Plan;
import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ThemesRepository extends JpaRepository<Theme, Long> {

    List<Questions> getThemeByPlan_Id(Long id);

    void deleteThemeById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Theme t SET t.title = :title, t.details = :details, t.Links = :links WHERE t.id = :id")
    void update(@Param("id") Long id, @Param("title") String title, @Param("details") String details, @Param("links") String links);



    //boolean existsByPlanAndId(Plan plan, Long uuid);

}
