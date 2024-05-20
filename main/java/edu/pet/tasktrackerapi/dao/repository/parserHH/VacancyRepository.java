package edu.pet.tasktrackerapi.dao.repository.parserHH;

import edu.pet.tasktrackerapi.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Vacancy findTopByOrderByIdDesc();

}
