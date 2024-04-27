package edu.pet.tasktrackerapi.repository.planner;

import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {

    List<Questions> getQuestByTheme_Id(Long id);

    void deleteQuestById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Questions t SET t.titleQuestions = :titleQuestions, t.nameThemes = :nameThemes, t.answer = :answer WHERE t.id = :id")
    String update(@Param("id") Long id, @Param("titleQuestions") String titleQuestions, @Param("nameThemes") String nameThemes, @Param("answer") String answer);




    boolean existsByThemeAndId(Theme theme, Long uuid);

}
