package es.cifpcm.IzquierdoBritoAndres.data.repositories;

import es.cifpcm.IzquierdoBritoAndres.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeesRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

}