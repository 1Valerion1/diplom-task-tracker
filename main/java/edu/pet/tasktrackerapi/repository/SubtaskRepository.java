package edu.pet.tasktrackerapi.repository;

import edu.pet.tasktrackerapi.api.model.Subtask;
import edu.pet.tasktrackerapi.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
   // List<Subtask> getSubtaskByTasks_Id(UUID id);

    void deleteTaskById(UUID uuid);

    int countSubtasksByTaskAndCompleted(Task task, boolean completed);


    @Transactional
    @Modifying
    @Query("UPDATE Subtask t SET t.title = :title,  t.details = :details, t.completed = :completed, t.completedAt = :completedAt WHERE t.id = :id")
    void update(@Param("id") UUID uuid, @Param("title") String title, @Param("details") String details,
                @Param("completed") boolean completed, @Param("completedAt") Timestamp completedAt);


    @Transactional
    @Modifying
    @Query("UPDATE Subtask t SET t.title = :title, t.details = :details WHERE t.id = :id")
    void updateCompleted(@Param("id") UUID uuid, @Param("title") String title, @Param("details") String details);


    boolean existsByTaskAndId(Task task, UUID uuid);


}
