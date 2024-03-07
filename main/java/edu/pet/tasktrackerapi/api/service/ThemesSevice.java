package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.api.model.Plan;
import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.exception.NotFoundException;
import edu.pet.tasktrackerapi.repository.planner.QuestionsRepository;
import edu.pet.tasktrackerapi.repository.planner.ThemesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemesSevice {

    private final ThemesRepository themesRepository;
    private final ModelMapper modelMapper;

//    public Long createQuestions(Plan plan, Theme theme) {
//        Theme question = Theme
//                .builder()
//                .questions(questions.getQuestions())
//                .theme(theme)
//                .build();
//
//        return themesRepository.save(question).getId();
//    }

    public List<Theme> getPlanThemes(Plan plan) {
        System.out.println(getUsersTasksEntities(plan));

        return modelMapper.map(
                getUsersTasksEntities(plan),
                new TypeToken<List<Theme>>() {
                }.getType()
        );
    }

    public List<Questions> getUsersTasksEntities(Plan plan) {
        return themesRepository.getThemeByPlan_Id(plan.getId());
    }
    // так как update  ничего не возвращает
    public void updateQuestions(Theme theme) {
        themesRepository.update(theme.getId(), theme.getTitle(), theme.getDetails(), theme.getLinks());
    }


//    @Transactional
//    public void deleteTask(Plan plan, Long id) {
//        if (themesRepository.existsByThemeAndId(plan, id)) {
//            deleteTaskByUUID(id);
//        } else {
//            throw new NotFoundException();
//        }
//    }

    public void deleteTheme (Long id) {
        themesRepository.deleteThemeById(id);
    }
}
