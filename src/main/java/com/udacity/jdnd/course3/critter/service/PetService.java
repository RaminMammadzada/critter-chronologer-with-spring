package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public Optional<Pet> getPet(long petId) {
        return petRepository.findById(petId);
    }

    public Pet createPet(Pet pet) {
        pet = petRepository.save(pet);
        return pet;
    }

    public Pet createPetWithOwner(Pet pet, Long ownerId) {
        Customer customer = customerService.getCustomerById(ownerId).orElseThrow(() -> new CustomerNotFoundException("ID: " + ownerId));
        pet.setCustomer(customer);
        pet = petRepository.save(pet);

        List<Pet> customerPets = customer.getPets();
        if(customerPets != null) {
            customerPets.add(pet);
        } else {
            customerPets = new ArrayList<>();
        }
        customer.setPets(customerPets);

        return pet;
    }

    public List<Pet> getPetsOfOwnerWithId(Long ownerId) {
        return petRepository.findAllByCustomerId(ownerId);
    }

    public Iterable<Pet> getAllPets() {
        return petRepository.findAll();
    }

}
