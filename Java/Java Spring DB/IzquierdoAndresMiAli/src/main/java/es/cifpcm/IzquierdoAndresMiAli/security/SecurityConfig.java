package es.cifpcm.IzquierdoAndresMiAli.security;

import es.cifpcm.IzquierdoAndresMiAli.data.services.GroupService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    private final GroupService groupService;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, GroupService groupService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.groupService = groupService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(auth ->auth
                .requestMatchers("/register","/").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login-error")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );

        http.logout(form -> form
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        http.userDetailsService(userService);
        return http.build();
    }


}
