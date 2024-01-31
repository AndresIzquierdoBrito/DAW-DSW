package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}