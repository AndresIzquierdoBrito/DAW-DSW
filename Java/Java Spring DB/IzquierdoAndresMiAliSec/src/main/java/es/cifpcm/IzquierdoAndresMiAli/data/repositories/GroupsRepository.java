package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GroupsRepository extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {

    @Query("select g from Group g where g.groupName = ?1")
    Group findByGroupName(String groupName);
}