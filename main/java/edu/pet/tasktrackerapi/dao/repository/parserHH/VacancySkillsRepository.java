package edu.pet.tasktrackerapi.dao.repository.parserHH;

import edu.pet.tasktrackerapi.model.VacancySkills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VacancySkillsRepository extends JpaRepository<VacancySkills, Long> {
    List<VacancySkills> findByVacancyId(Long vacancyId);

}
