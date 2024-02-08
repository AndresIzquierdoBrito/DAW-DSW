package es.cifpcm.IzquierdoAndresMiAli.security;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.GroupsRepository;
import es.cifpcm.IzquierdoAndresMiAli.data.repositories.UsersRepository;
import es.cifpcm.IzquierdoAndresMiAli.data.services.GroupService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    private final GroupService groupService;

    private final PasswordEncoder passwordEncoder;

    private final GroupsRepository groupsRepository;

    private final UsersRepository usersRepository;


    public SecurityConfig(UserService userService, GroupService groupService, PasswordEncoder passwordEncoder,
                          GroupsRepository groupsRepositoryy, UsersRepository usersRepository) {
        this.userService = userService;
        this.groupService = groupService;
        this.passwordEncoder = passwordEncoder;
        this.groupsRepository = groupsRepositoryy;
        this.usersRepository = usersRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->auth
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login")
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

    private static final String[] AUTH_WHITELIST = {
            "/resources/**",
            "/static/**",
            "/styles/**",
            "/uploads/**",
            "/assets/**",
            "/productos",
            "/register",
            "/"
    };

    public void configure(WebSecurity web) {
        web.expressionHandler(new DefaultWebSecurityExpressionHandler() {
            @Override
            protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication auth, FilterInvocation fi) {
                WebSecurityExpressionRoot root = (WebSecurityExpressionRoot) super.createSecurityExpressionRoot(auth, fi);
                root.setDefaultRolePrefix("");
                return root;
            }
        });
    }

    @Bean
    public boolean initializeData()
    {
        createGroupIfNotFound("administradores");
        createGroupIfNotFound("gestores");
        createGroupIfNotFound("clientes");

        createUserIfNotFound("admin","admin", "administradores");
        createUserIfNotFound("gestor","gestor", "gestores");
        createUserIfNotFound("cliente","cliente", "clientes");

        return true;
    }

    private void createGroupIfNotFound(String groupName) {
        if (groupService.findByGroupName(groupName) == null) {
            Group role = new Group();
            role.setGroupName(groupName);
            groupsRepository.save(role);
        }
    }

    private void createUserIfNotFound(String name, String password, String roleName) {
        if (userService.getByUsername(name) == null) {
            User user = new User();
            user.setUserName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setUserId(usersRepository.getMaxId() + 1);
            Group role = groupsRepository.findByGroupName(roleName);
            if(role != null)
            {
                user.setGroups(Collections.singletonList(role));
                usersRepository.save(user);
            }
        }
    }

}
