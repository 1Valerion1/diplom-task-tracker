package edu.pet.tasktrackerapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pet.tasktrackerapi.api.model.User;
import edu.pet.tasktrackerapi.repository.planner.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;


//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        return email -> userRepository
//                .findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

    // Блядь, он принимает имя пользователя а не логин в этом и ошибка
    @Bean
    public UserDetailsService userDetailsService(){

        return email -> {
            try {

                Long id = Long.parseLong(email);
                User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                return user;

            } catch (NumberFormatException e) {

                return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            }
        };
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){


        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
