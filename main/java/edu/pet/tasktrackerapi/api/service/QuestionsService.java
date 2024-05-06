package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.api.model.Questions;
import edu.pet.tasktrackerapi.api.model.Theme;
import edu.pet.tasktrackerapi.exception.NotFoundException;
import edu.pet.tasktrackerapi.repository.planner.QuestionsRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Long createQuestions( Questions questions) {
        Questions question = Questions
                .builder()
                .titleQuestions(questions.getTitleQuestions())
                .nameThemes(questions.getNameThemes())
                .answer(questions.getAnswer())
                .theme(questions.getTheme())
                .build();

        return questionsRepository.save(question).getId();
    }
    public List<Questions> getAllQuestions(){
        return questionsRepository.findAll();
    }
    public List<Questions> getThemeQuestions(Long id){
        return questionsRepository.getQuestByTheme_Id(id);
    }

//    public List<Questions> getThemesQuestions(Theme theme) {
//        System.out.println(getTemesQuestEntities(theme));
//
//        return modelMapper.map(
//                getTemesQuestEntities(theme),
//                new TypeToken<List<Questions>>() {
//                }.getType()
//        );
//    }

    public List<Questions> getTemesQuestEntities(Theme theme) {
        return questionsRepository.getQuestByTheme_Id(theme.getId());
    }
// так как update  ничего не возвращает
    public void updateQuestion(Questions questions) {
         questionsRepository.update(questions.getId(), questions.getTitleQuestions(),
                 questions.getNameThemes(),questions.getAnswer());
    }

    public Questions updateQuestions(Questions questions) {
        // Находим существующий вопрос по ID
        Questions existingQuestion = questionsRepository.findById(questions.getId())
                .orElseThrow(() -> new EntityNotFoundException("Вопрос с указанным ID не найден"));

        existingQuestion.setTitleQuestions(questions.getTitleQuestions());
        existingQuestion.setNameThemes(questions.getNameThemes());
        existingQuestion.setAnswer(questions.getAnswer());

        return questionsRepository.save(existingQuestion);
    }


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

