package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer mappedCustomer = modelMapper.map(customerDTO, Customer.class);
        Customer createdCustomer = customerService.createCustomer(mappedCustomer);
        return modelMapper.map(createdCustomer, CustomerDTO.class);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<CustomerDTO>();
        for (Customer c : customerService.getAllCustomers()) {
            CustomerDTO customerDTO = modelMapper.map(c, CustomerDTO.class);
            List<Pet> pets = c.getPets();
            List<Long> petIds = new ArrayList<Long>();
            if(pets != null) {
                for (Pet p : pets) {
                    petIds.add(p.getId());
                }
            }
            customerDTO.setPetIds(petIds);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId).orElseThrow(() -> new PetNotFoundException("ID: " + petId));
        Optional<Customer> customer = customerService.getCustomerById(pet.getCustomer().getId());
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee mappedEmployee = modelMapper.map(employeeDTO, Employee.class);
        Employee createdEmployee = employeeService.createEmployee(mappedEmployee);
        return modelMapper.map(createdEmployee, EmployeeDTO.class);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee retrievedEmployee = employeeService.getEmployee(employeeId).orElseThrow(() -> new EmployeeNotFoundException("ID: " + employeeId));
        return modelMapper.map(retrievedEmployee, EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailableDaysOfEmployee(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
