package edu.pet.tasktrackerapi.configuration.security;

import edu.pet.tasktrackerapi.configuration.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    //
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf()
                .disable()
                .authorizeHttpRequests((authz) -> authz
                      //  .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers("/api/v1/auth/**")
                .requestMatchers("/api/v1/statistics/**")
                .requestMatchers("/api/v1/questions/**")
                .requestMatchers("/api/v1/plans/**")
                .requestMatchers("/api/v1/tasks")
                .requestMatchers("/api/v1/themes/**")
                .requestMatchers("/api/v1/ouroboros")

                .requestMatchers("/api/v1/user/**")

                .requestMatchers("/v3/api-docs/**")
                .requestMatchers("configuration/**")
                .requestMatchers("/swagger*/**")
                .requestMatchers("/webjars/**")
                .requestMatchers("/swagger-ui/**")
                .requestMatchers("swagger-ui.html")
                .requestMatchers("/swagger-resources/**");
    }
}
