package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.GroupsRepository;
import es.cifpcm.IzquierdoAndresMiAli.data.repositories.UsersRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService  implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final GroupsRepository groupsRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, GroupsRepository groupsRepository) {
        this.usersRepository = usersRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario no existente.");
        }

        List<GrantedAuthority> authorities = user.getGroups().stream()
                .map(role -> new SimpleGrantedAuthority(role.getGroupName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    public User newUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Group role = groupsRepository.findById(3).orElse(null);
        List<Group> authorities = new ArrayList<>();
        authorities.add(role);
        user.setGroups(authorities);
        user.setUserId(usersRepository.getMaxId() + 1);
        usersRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return usersRepository.getUsersSortedIdDesc();
    }

    public boolean userExists(String username) {
        return usersRepository.findByUserName(username) != null;
    }

    public boolean userExists(Integer userId) {
        return usersRepository.findById(userId).isPresent();
    }

    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }

    public User getById(Integer id) {
        return requireOne(id);
    }

    public User getByUsername(String username) {
        return usersRepository.findByUserName(username);
    }

    private User requireOne(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }


}
