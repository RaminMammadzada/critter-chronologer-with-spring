package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule createSchedule(Schedule schedule, List<Long> petIds, List<Long> employeeIds) {
        List<Employee> employees = new ArrayList<>();
        employeeIds.forEach(id ->
            employees.add(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("ID: " + id))
        ));
        schedule.setEmployees(employees);

        List<Pet> pets = new ArrayList<>();
        petIds.forEach(id ->
                pets.add(petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("ID: " + id))
                ));
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {

        return (List<Schedule>) scheduleRepository.findAll();
    }

    public List<Schedule> retrieveScheduleForPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("ID: " + petId));
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        return schedules.stream()
                .filter(schedule -> schedule.getPets().contains(pet)).collect(Collectors.toList());
    }

    public List<Schedule> retrieveScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("ID: " + employeeId));
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        return schedules.stream()
                .filter(schedule -> schedule.getEmployees().contains(employee)).collect(Collectors.toList());
    }

    public List<Schedule> retrieveScheduleForCustomer(long customerId) {
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        return schedules.stream()
                .filter(schedule -> schedule.getPets().stream().allMatch(pet -> pet.getCustomer().getId().equals(customerId))).collect(Collectors.toList());
    }
}
