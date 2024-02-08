package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.UsersRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Integer save(User user) {
        User bean = new User();
        BeanUtils.copyProperties(user, bean);
        bean = usersRepository.save(bean);
        return bean.getUserId();
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
