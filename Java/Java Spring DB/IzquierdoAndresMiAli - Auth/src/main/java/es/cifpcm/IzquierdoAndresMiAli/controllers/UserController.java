package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String save(@Valid @RequestBody User vO) {
        return userService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody User vO) {
        userService.update(id, vO);
    }

    @GetMapping("/{id}")
    public User getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    public Page<User> query(@Valid User user) {
        return userService.query(user);
    }
}
