package edu.pet.tasktrackerapi.service;

import edu.pet.tasktrackerapi.model.Plan;
import edu.pet.tasktrackerapi.model.Theme;
import edu.pet.tasktrackerapi.dao.exception.NotFoundException;
import edu.pet.tasktrackerapi.dao.repository.planner.ThemesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemesSevice {

    private final ThemesRepository themesRepository;
    private final ModelMapper modelMapper;

    public Long createTheme(Theme theme) {
        Plan plan = new Plan(2l,"Java");
        Theme themes = Theme
                .builder()
                .title(theme.getTitle())
                .details(theme.getDetails())
                .links(theme.getLinks())
                .plan(plan)
                .build();

        return themesRepository.save(themes).getId();
    }

    public List<Theme> findByName(String name){
        return themesRepository.findByTitle(name);
    }

    public List<Theme> getAllThemes(){
        return themesRepository.findAll();
    }
    public Theme getThemesId(Long id) {
        Optional<Theme> themeOptional = themesRepository.findById(id);
        if (themeOptional.isPresent()) {
            return themeOptional.get();
        } else {
            throw new NotFoundException();
            // Обработка ситуации, когда тема не найдена
            // Можно выбросить исключение, вернуть пустую тему или что-то еще в зависимости от контекста
        }
    }


//    public List<Theme> getPlanThemes(Plan plan) {
//        System.out.println(getUsersTasksEntities(plan));
//
//        return modelMapper.map(
//                getUsersTasksEntities(plan),
//                new TypeToken<List<Theme>>() {
//                }.getType()
//        );
//    }


//    public List<Questions> getUsersTasksEntities(Plan plan) {
//        return themesRepository.getThemeByPlan_Id(plan.getId());
//    }
    // так как update  ничего не возвращает
    public void updateTheme(Theme theme) {
        themesRepository.update(theme.getId(), theme.getTitle(), theme.getDetails(), theme.getLinks());
    }

    @Transactional
    public void deleteTheme (Long id) {
        themesRepository.deleteThemeById(id);
    }


//    public void deleteTask(Long id) {
//        if (themesRepository.deleteThemeById(id)) {
//            deleteThemeById(id);
//        } else {
//            throw new NotFoundException();
//        }
//    }

}
