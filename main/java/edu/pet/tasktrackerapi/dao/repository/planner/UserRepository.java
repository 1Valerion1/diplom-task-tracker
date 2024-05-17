package edu.pet.tasktrackerapi.dao.repository.planner;

import edu.pet.tasktrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String user);
    boolean existsEmailByEmail(String email);

    boolean existsByUsername(String email);


}
