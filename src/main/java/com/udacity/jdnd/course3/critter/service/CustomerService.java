package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public List<CustomerDTO> getAllCustomerDTOs() {
        List<CustomerDTO> customerDTOS = new ArrayList<CustomerDTO>();
        for (Customer c : this.getAllCustomers()) {
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

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
