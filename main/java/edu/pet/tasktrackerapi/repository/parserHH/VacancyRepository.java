package edu.pet.tasktrackerapi.repository.parserHH;

import edu.pet.tasktrackerapi.api.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

}
