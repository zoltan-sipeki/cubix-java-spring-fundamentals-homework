package hu.cubix.hr.zoltan_sipeki.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Service
public abstract class AbstractEmployeeService implements EmployeeService {
    private Map<Long, Employee> employees = new HashMap<>();

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployeeById(long id) {
        return employees.get(id);
    }

    public List<Employee> getEmployeesByMinSalary(int minSalary) {
        List<Employee> list = new ArrayList<>();
        for (var employee : employees.values()) {
            if (employee.getSalary() > minSalary) {
                list.add(employee);
            }
        }
        return list;
    }

    public Employee createEmployee(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            return null;
        }

        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        if (!employees.containsKey(employee.getId())) {
            return null;
        }

        employees.put(employee.getId(), employee);
        return employee;
    }

    public void deleteEmployeeById(long id) {
        employees.remove(id);
    }
}
