package com.biblio.xpress.config;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailsService customUserDetailsService) {
        this.authEntryPoint = authEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/css/style.css").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/member-dashboard").permitAll()
                                .requestMatchers("/librarian-dashboard").permitAll()
                                .requestMatchers("/admin-dashboard").permitAll()
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
                ).formLogin(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

}
