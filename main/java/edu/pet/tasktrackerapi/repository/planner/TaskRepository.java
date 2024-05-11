package edu.pet.tasktrackerapi.repository.planner;

import edu.pet.tasktrackerapi.api.model.Enum.Priorities;
import edu.pet.tasktrackerapi.api.model.Task;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.repository.planner.mapper.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> { // тут поменял на TaskEntity с Task
    List<Task> getTasksByUser_Id(Long id);

    void deleteTaskById(Long id);

    int countTasksByUserAndCompleted(User user, boolean completed);

    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Task findTaskById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.details = :details, t.completed = :completed, t.completedAt = :completedAt,t.priorities = :priorities WHERE t.id = :id")
    void update(@Param("id") Long uuid,
                @Param("title") String title, @Param("details") String details,
                @Param("completed") boolean completed, @Param("completedAt")Timestamp completedAt,
                @Param("priorities") Priorities priorities);


    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.details = :details,t.priorities = :priorities WHERE t.id = :id")
    void updateCompleted(@Param("id") Long uuid, @Param("title") String title, @Param("details") String details, @Param("priorities") Priorities priorities);


    boolean existsByUserAndId(User user, Long uuid);


}
