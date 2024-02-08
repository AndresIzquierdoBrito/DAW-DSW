package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "login";
    }

    @GetMapping("/register")
    public String registerView() {
        return "usuario/create";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model,
                           HttpSession session) {
        if(userService.userExists(username)){
            model.addAttribute("error", "Ya existe un usuario con ese nombre.");
            return "usuario/create";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas deben ser iguales.");
            return "usuario/create";
        }

        User userToRegister = new User();
        userToRegister.setUserName(username);
        userToRegister.setPassword(password);
        User registeredUser = userService.newUser(userToRegister);

        session.setAttribute("user", registeredUser);
        return "redirect:/login";
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('administradores')")
    public String listUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getByUsername(username);
        List<User> users = userService.getAllUsers();
        users.remove(user);
        model.addAttribute("users", users);
        return "usuario/lista";
    }

    @PostMapping("/user/{id}/delete")
    @PreAuthorize("hasRole('administradores')")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/usuarios";
    }
}
