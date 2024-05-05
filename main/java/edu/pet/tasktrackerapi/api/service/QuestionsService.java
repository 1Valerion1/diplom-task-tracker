package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.exception.NotFoundException;
import edu.pet.tasktrackerapi.repository.planner.QuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionsService {
    private final QuestionsRepository questionsRepository;
    private final ModelMapper modelMapper;

    public Long createQuestions(Theme theme, Questions questions) {
        Questions question = Questions
                .builder()
                .titleQuestions(questions.getTitleQuestions())
                .nameThemes(questions.getNameThemes())
                .answer(questions.getAnswer())
                .theme(theme)
                .build();

        return questionsRepository.save(question).getId();
    }

    public List<Questions> getThemesQuestions(Theme theme) {
        System.out.println(getUsersTasksEntities(theme));

        return modelMapper.map(
                getUsersTasksEntities(theme),
                new TypeToken<List<Questions>>() {
                }.getType()
        );
    }
    public List<Questions> getAllQuestions(){
        return questionsRepository.findAll();
    }

    public List<Questions> getUsersTasksEntities(Theme theme) {
        return questionsRepository.getQuestByTheme_Id(theme.getId());
    }
// так как update  ничего не возвращает
    public void updateQuestions(Questions questions) {
         questionsRepository.update(questions.getId(), questions.getTitleQuestions(), questions.getNameThemes(),questions.getAnswer());
    }

//    public Questions updateQuestions(Questions questions) {
//        // Находим существующий вопрос по ID
//        Questions existingQuestion = questionsRepository.findById(questions.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Вопрос с указанным ID не найден"));
//
//        existingQuestion.setQuestions(questions.getQuestions());
//
//        return questionsRepository.save(existingQuestion);
//    }


    @Transactional
    public void deleteQuest(Theme theme, Long id) {
        if (questionsRepository.existsByThemeAndId(theme, id)) {
            deleteTaskByUUID(id);
        } else {
            throw new NotFoundException();
        }
    }

    protected void deleteTaskByUUID(Long id) {
        questionsRepository.deleteQuestById(id);
    }
}

