package edu.pet.tasktrackerapi.repository;

import edu.pet.tasktrackerapi.model.Enum.Role;
import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.dao.repository.planner.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByUsername() {
        //given
        String username = "test";
        User expected = User.builder()
                .username(username)
                .password("test")
                .role(Role.USER)
                .build();
        underTest.save(expected);

        underTest.save(expected);

        //when
        User actual = underTest.findByUsername(username).orElseThrow();

        //then
        assertThat(actual).isEqualTo(expected);

    }


    @Test
    void checkIfExistsUserByUsername() {

        //given
        String username = "test";
        User user = User.builder()
                .username(username)
                .password("test")
                .role(Role.USER)
                .build();
        underTest.save(user);

        //when
        boolean actual = underTest.existsUserByUsername(username);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    void checkIfNotExistsUserByUsername() {
        //given
        String username = "notExist";

        //when
        boolean actual = underTest.existsUserByUsername(username);

        //then
        assertThat(actual).isFalse();
    }


}