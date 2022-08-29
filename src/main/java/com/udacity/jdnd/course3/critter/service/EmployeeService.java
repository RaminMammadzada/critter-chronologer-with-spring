package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }


    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setAvailableDaysOfEmployee(Long employeeId, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("ID: " + employeeId));;
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }
}
