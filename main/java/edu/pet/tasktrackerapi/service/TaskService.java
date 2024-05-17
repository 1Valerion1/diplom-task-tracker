package edu.pet.tasktrackerapi.service;

import edu.pet.tasktrackerapi.dao.dto.NewTaskRequest;
import edu.pet.tasktrackerapi.dao.dto.TaskDto;
import edu.pet.tasktrackerapi.model.Task;
import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.dao.exception.NotFoundException;
import edu.pet.tasktrackerapi.dao.repository.planner.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public Long createTask(User user, NewTaskRequest newTaskRequest) {

        Task newTask = Task
                .builder()
                .title(newTaskRequest.getTitle())
                .details(newTaskRequest.getDetails())
                .priorities(newTaskRequest.getPriorities())
                .completed(false)
                .user(user)
                .build();

        return taskRepository.save(newTask).getId();
    }

    public Task getIdTask(Long taskId){
        Task task = taskRepository.findTaskById(taskId);
        if (task == null) {
            return null;
        }
        return task;
    }

    public List<TaskDto> getUsersTasksDto(User user) {
        System.out.println(getUsersTasksEntities(user));
        return modelMapper.map(
                getUsersTasksEntities(user),
                new TypeToken<List<TaskDto>>() {
                }.getType()
        );
    }

    public List<Task> getUsersTasksEntities(User user) {
        return taskRepository.getTasksByUser_Id(user.getId());
    }

    @Transactional
    public void updateTaskIfBelongsToUser(User user, TaskDto taskDto) {
        if (!taskRepository.existsByUserAndId(user, taskDto.getId())) {
            throw new NotFoundException();
        }
       // TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
        Task task = taskRepository.findById(taskDto.getId()).get();
        if ((taskDto.isCompleted() && !task.isCompleted())) {
            completeTask(taskDto);

        } else if (taskDto.isCompleted()) {
            updateCompletedTask(taskDto);

        } else {
            updateTask(taskDto);
        }

    }

    protected void updateTask(TaskDto task) {
        taskRepository.update(task.getId(), task.getTitle(), task.getDetails(),
                task.isCompleted(), null, task.getPriorities());
    }

    protected void completeTask(TaskDto task) {
        taskRepository.update(task.getId(), task.getTitle(), task.getDetails(),
                task.isCompleted(), Timestamp.valueOf(LocalDateTime.now()), task.getPriorities());
    }

    protected void updateCompletedTask(TaskDto task) {
        taskRepository.updateCompleted(task.getId(), task.getTitle(), task.getDetails(), task.getPriorities());
    }


    @Transactional
    public void deleteTask(User user, Long id) {
        if (taskRepository.existsByUserAndId(user, id)) {
            deleteTaskByUUID(id);
        } else {
            throw new NotFoundException();
        }
    }

    protected void deleteTaskByUUID(Long id) {
        taskRepository.deleteTaskById(id);
    }

}
