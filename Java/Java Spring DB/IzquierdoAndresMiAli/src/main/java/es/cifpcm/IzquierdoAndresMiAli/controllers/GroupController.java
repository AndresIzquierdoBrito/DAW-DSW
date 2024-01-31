package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.GroupService;
import es.cifpcm.IzquierdoAndresMiAli.models.Group;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public String save(@Valid @RequestBody Group vO) {
        return groupService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        groupService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Group vO) {
        groupService.update(id, vO);
    }

    @GetMapping("/{id}")
    public Group getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return groupService.getById(id);
    }

    @GetMapping
    public Page<Group> query(@Valid Group group) {
        return groupService.query(group);
    }
}
