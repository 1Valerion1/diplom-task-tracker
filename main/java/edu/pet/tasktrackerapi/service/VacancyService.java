package edu.pet.tasktrackerapi.service;

import edu.pet.tasktrackerapi.dao.dto.VacancyDTO;
import edu.pet.tasktrackerapi.model.Vacancy;
import edu.pet.tasktrackerapi.model.VacancySkills;
import edu.pet.tasktrackerapi.dao.repository.parserHH.VacancyRepository;
import edu.pet.tasktrackerapi.dao.repository.parserHH.VacancySkillsRepository;
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

    public void saveSkills(Map<String, Integer> skillsKey, Vacancy vac) {
        for (Map.Entry<String, Integer> entry : skillsKey.entrySet()) {
            VacancySkills vacancySkills = new VacancySkills();
            vacancySkills.setSkillName(entry.getKey());
            vacancySkills.setSkillCount(entry.getValue());
            vacancySkills.setVacancy(vac);
            vacancySkillRepository.save(vacancySkills);

        }

    }

    public Vacancy saveProfession(VacancyDTO vacancyDTO) {

        Vacancy vacancy = new Vacancy();
        vacancy.setProfession(vacancyDTO.getProfession());
        vacancy.setNumberVacancies(vacancyDTO.getNumberVacancies());
        vacancy.setUrl(vacancyDTO.getAlternateUrl());

        Vacancy vacSave = vacancyRepository.save(vacancy);
        return  vacSave;
    }

    public List<Vacancy> getallData() {
             return vacancyRepository.findAll();
    }


    public List<VacancySkills> getAllSkils() {
        return vacancySkillRepository.findAll();
    }
}
