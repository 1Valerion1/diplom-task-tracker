package edu.pet.tasktrackerapi.ParserHH.service;

import edu.pet.tasktrackerapi.ParserHH.dto.VacancyDTO;
import edu.pet.tasktrackerapi.ParserHH.model.Vacancy;
import edu.pet.tasktrackerapi.ParserHH.model.VacancySkills;
import edu.pet.tasktrackerapi.repository.parserHH.VacancyRepository;
import edu.pet.tasktrackerapi.repository.parserHH.VacancySkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VacancyService {
    @Autowired
    private VacancySkillsRepository vacancySkillRepository;
    @Autowired
    private VacancyRepository vacancyRepository;

    public void saveSkills(Map<String, Integer> skillsKey) {
        for (Map.Entry<String, Integer> entry : skillsKey.entrySet()) {
            VacancySkills vacancySkills = new VacancySkills();
            vacancySkills.setSkillName(entry.getKey());
            vacancySkills.setSkillCount(entry.getValue());
            vacancySkillRepository.save(vacancySkills);

        }

    }

    public void saveProfession(VacancyDTO vacancyDTO) {

        Vacancy vacancy = new Vacancy();
        vacancy.setProfession(vacancyDTO.getProfession());
        vacancy.setNumberVacancies(vacancyDTO.getNumberVacancies());
        vacancy.setUrl(vacancyDTO.getAlternateUrl());

        vacancyRepository.save(vacancy);
    }

    public List<Vacancy> getallData() {
             return vacancyRepository.findAll();
    }


    public List<VacancySkills> getAllSkils() {
        return vacancySkillRepository.findAll();
    }
}
