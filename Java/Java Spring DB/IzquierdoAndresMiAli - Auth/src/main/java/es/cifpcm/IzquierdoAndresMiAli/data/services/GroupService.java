package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.GroupsRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GroupService {

    private final GroupsRepository groupsRepository;

    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public Integer save(Group group) {
        Group bean = new Group();
        BeanUtils.copyProperties(group, bean);
        bean = groupsRepository.save(bean);
        return bean.getGroupId();
    }

    public void delete(Integer id) {
        groupsRepository.deleteById(id);
    }

    public void update(Integer id, Group group) {
        Group bean = requireOne(id);
        BeanUtils.copyProperties(group, bean);
        groupsRepository.save(bean);
    }

    public Group getById(Integer id) {
        return requireOne(id);
    }

    public Page<Group> query(Group group) {
        throw new UnsupportedOperationException();
    }


    private Group requireOne(Integer id) {
        return groupsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
