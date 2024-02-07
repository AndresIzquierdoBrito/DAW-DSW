package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.GroupsRepository;
import es.cifpcm.IzquierdoAndresMiAli.data.repositories.UsersRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    public Integer createUser(User user) {
        User bean = new User();
        BeanUtils.copyProperties(user, bean);
        bean = usersRepository.save(bean);
        return bean.getUserId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }

    public void update(Integer id, User user) {
        User bean = requireOne(id);
        BeanUtils.copyProperties(user, bean);
        usersRepository.save(bean);
    }

    public User getById(Integer id) {
        return requireOne(id);
    }

    public Page<User> query(User user) {
        throw new UnsupportedOperationException();
    }

    private User requireOne(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }


}
