package edu.pet.tasktrackerapi.auth.service;

import edu.pet.tasktrackerapi.model.User;
import edu.pet.tasktrackerapi.dao.repository.planner.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return user;
    }


    public User loadUserById(Long id) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return user;
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return user;
    }
}
