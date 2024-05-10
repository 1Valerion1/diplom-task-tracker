package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.api.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.api.dto.SubtaskDTO;
import edu.pet.tasktrackerapi.api.model.Subtask;
import edu.pet.tasktrackerapi.api.model.Task;
import edu.pet.tasktrackerapi.exception.NotFoundException;
import edu.pet.tasktrackerapi.repository.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final ModelMapper modelMapper;


    public Long createSubtask(Task task, NewTaskRequest newTaskRequest) {

        Subtask newTask = Subtask
                .builder()
                .title(newTaskRequest.getTitle())
                .details(newTaskRequest.getDetails())
                .priorities(newTaskRequest.getPriorities())
                .completed(false)
                .task(task)
                .build();

        return subtaskRepository.save(newTask).getId();
    }

    public List<Subtask> getSubtaskAll(){

        return subtaskRepository.findAll();
    }

    public List<SubtaskDTO> getSubtask(Task task){
        System.out.println(getTasksSubtasksEntities(task));
        return modelMapper.map(
                getTasksSubtasksEntities(task),
                new TypeToken<List<SubtaskDTO>>(){}.getType()
        );
    }
    public List<Subtask> getTasksSubtasksEntities(Task task){
        return subtaskRepository.getSubtasksByTask_Id(task.getId());
    }


//
//    @Transactional
//    public void updateTaskIfBelongsToUser(Task task, SubtaskDTO taskDto) {
//        if (!subtaskRepository.existsByTaskAndId(task, taskDto.getId())){
//            throw new NotFoundException();
//        }
//        Subtask subtask = subtaskRepository.findById(taskDto.getId()).get();
//        if ((taskDto.isCompleted() && !task.isCompleted())){
//            completeTask(taskDto);
//
//        } else if (taskDto.isCompleted()){
//            updateCompletedTask(taskDto);
//
//        } else {
//            updateTask(taskDto);
//        }
//
//    }
//    protected void updateTask(SubtaskDTO subtask){
//        subtaskRepository.update(subtask.getId(), subtask.getTitle(), subtask.getDetails(), subtask.isCompleted(), null);
//    }
//
//    protected void completeTask(SubtaskDTO subtask){
//        subtaskRepository.update(subtask.getId(), subtask.getTitle(), subtask.getDetails(), subtask.isCompleted(), Timestamp.valueOf(LocalDateTime.now()));
//    }
//
//    protected void updateCompletedTask(SubtaskDTO subtask){
//        subtaskRepository.updateCompleted(subtask.getId(), subtask.getTitle(), subtask.getDetails());
//    }


    // Помни что если у подзадачи есть id, то его надо тоже учитвывать.
    @Transactional
    public void deleteSubtask(Task task, Long uuid){
        if (subtaskRepository.existsByTaskIdAndId(task.getId(), uuid)) {
            deleteTaskByUUID(uuid);
            System.out.println("Subtask deleted successfully: " + uuid);
        } else {
            System.out.println("Subtask not found: " + uuid);
            throw new NotFoundException();//"Subtask not found"
        }
    }
    protected void deleteTaskByUUID(Long uuid){
        subtaskRepository.deleteTaskById(uuid);
    }

}
