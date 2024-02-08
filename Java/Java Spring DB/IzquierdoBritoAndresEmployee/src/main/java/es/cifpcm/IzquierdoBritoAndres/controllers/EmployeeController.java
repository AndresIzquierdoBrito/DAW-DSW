package es.cifpcm.IzquierdoBritoAndres.controllers;

import es.cifpcm.IzquierdoBritoAndres.data.services.EmployeeService;
import es.cifpcm.IzquierdoBritoAndres.models.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model){
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "create";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute("employee") Employee employee, Model model) {
        employeeService.save(employee);
        model.addAttribute("employee", employee);
        return "redirect:/employee";
    }

    @PostMapping
    public String save(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee).toString();
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@Valid @NotNull @PathVariable("id") Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }

    @GetMapping("/{id}/edit")
    public String updateEmployeeForm(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Employee employeeById = employeeService.getById(id);
        model.addAttribute("employee", employeeById);

        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployee(@Valid @NotNull @PathVariable("id") Integer id,
                                 @Valid @ModelAttribute("employee") Employee employee) {
        employeeService.update(id, employee);

        return "redirect:/employee/" + id;
    }

    @GetMapping("/{id}")
    public String getById(@Valid @NotNull @PathVariable("id") Integer id, Model model) {
        Employee employeeById = employeeService.getById(id);
        model.addAttribute("employee", employeeById);
        return "details";
    }

}
