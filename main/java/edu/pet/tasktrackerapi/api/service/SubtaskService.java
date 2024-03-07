package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.repository.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
    private final ModelMapper modelMapper;

//
//    public UUID createTask(Task task, NewTaskRequest newTaskRequest){
//        Subtask newTask = Subtask
//                .builder()
//                .title(newTaskRequest.getTitle())
//                .details(newTaskRequest.getDetails())
//                .completed(false)
//                .task(task)
//                .build();
//        return subtaskRepository.save(newTask).getId();
//    }
//
//    public List<SubtaskDTO> getUsersTasksDto(Task task){
//        System.out.println(getTasksSubtasksEntities(task));
//        return modelMapper.map(
//                getTasksSubtasksEntities(task),
//                new TypeToken<List<SubtaskDTO>>(){}.getType()
//        );
//    }
//    public List<Subtask> getTasksSubtasksEntities(Task task){
//        return subtaskRepository.getSubtaskByTasks_Id(task.getId());
//    }
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
//
//
//    @Transactional
//    public void deleteSubtask(Task task, UUID uuid){
//        if (subtaskRepository.existsByTaskAndId(task, uuid)){
//            deleteTaskByUUID(uuid);
//        } else {
//            throw new NotFoundException();
//        }
//    }
//    protected void deleteTaskByUUID(UUID uuid){
//        subtaskRepository.deleteTaskById(uuid);
//    }

}
