package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query("select u from User u where u.userName = ?1")
    User findByUserName(String userName);


    @Query("select max(u.userId), 0 from User u")
    Integer getMaxId();

    @Query("select u from User u order by u.userId DESC")
    List<User> getUsersSortedIdDesc();
}