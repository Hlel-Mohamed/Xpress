package com.biblio.xpress.config;

import com.biblio.xpress.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/css/style.css").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/member-dashboard").hasAuthority("MEMBER")
                                .requestMatchers("/librarian-dashboard").hasAuthority("LIBRARIAN")
                                .requestMatchers("/admin-dashboard").hasAuthority("ADMIN")
                                .requestMatchers("/add-book").permitAll()
                                .requestMatchers("/add-new-book").permitAll()
                                .requestMatchers("/members/**").permitAll()
                                .requestMatchers("/books/**").permitAll()
                                .requestMatchers("/borrowedBooks/**").permitAll()
                                .requestMatchers("/borrowed/**").permitAll()
                                .requestMatchers("/notifications/**").permitAll()
                                .requestMatchers("/my-notifications/**").permitAll()
                                .requestMatchers("/my-card/").permitAll()
                                .requestMatchers("/my-cards/").permitAll()
                                .requestMatchers("/myCategories").permitAll()
                                .requestMatchers("/newCategory/**").permitAll()
                                .requestMatchers("/myFavCategories").permitAll()
                                .requestMatchers("/AllCategories").permitAll()
                                .requestMatchers("/Categories").permitAll()
                                .requestMatchers("/search").permitAll()

                                .anyRequest().authenticated()
                ).formLogin(login->login
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            }
        };
    }

}
