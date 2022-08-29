package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Pet createdPet = petService.createPetWithOwner(pet, petDTO.getOwnerId());
        PetDTO mappedPet = modelMapper.map(createdPet, PetDTO.class);
        mappedPet.setOwnerId(createdPet.getCustomer().getId());
        return mappedPet;

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> retrievedPet = petService.getPet(petId);
        return modelMapper.map(retrievedPet, PetDTO.class);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOS = new ArrayList<PetDTO>();
        for (Pet p : petService.getAllPets()) {
            petDTOS.add(modelMapper.map(p, PetDTO.class));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<PetDTO>();
        for (Pet p : petService.getPetsOfOwnerWithId(ownerId)) {
            PetDTO petDTO = modelMapper.map(p, PetDTO.class);
            petDTO.setOwnerId(ownerId);
            petDTOS.add(petDTO);
        }
        return petDTOS;
    }
}
