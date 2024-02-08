package es.cifpcm.IzquierdoBritoAndres.data.services;

import es.cifpcm.IzquierdoBritoAndres.data.repositories.EmployeesRepository;
import es.cifpcm.IzquierdoBritoAndres.models.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<Employee> getAllEmployees() {
        return employeesRepository.findAll();
    }

    public Integer save(Employee employee) {
        Employee bean = new Employee();
        BeanUtils.copyProperties(employee, bean);
        bean = employeesRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        employeesRepository.deleteById(id);
    }

    public void update(Integer id, Employee employee) {
        Employee bean = requireOne(id);
        BeanUtils.copyProperties(employee, bean);
        employeesRepository.save(bean);
    }

    public Employee getById(Integer id) {
        Employee original = requireOne(id);
        return toDTO(original);
    }

    private Employee toDTO(Employee original) {
        Employee bean = new Employee();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Employee requireOne(Integer id) {
        return employeesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
