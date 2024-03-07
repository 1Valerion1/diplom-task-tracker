package edu.pet.tasktrackerapi.api.service;

import edu.pet.tasktrackerapi.api.dto.UserDto;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.exception.NotFoundException;
import edu.pet.tasktrackerapi.repository.planner.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserDto getUserInfo(User user) {

        return modelMapper.map(findByEmail(user.getEmail()), UserDto.class);
    }


    protected User getUserEntity(Long id) {
        //todo throw ex
        return userRepository.findById(id)
                .orElseThrow((Supplier<NotFoundException>) NotFoundException::new);
    }


    public User findByEmail(String email) {
        String rtr = email;
        User appUser = userRepository.findByEmail(email).orElseThrow();
        //log.info("User with email: {} - successfully found", appUser.getEmail());
        return appUser;
    }

    public User save(User appUser) {
        return userRepository.save(appUser);
    }

}
