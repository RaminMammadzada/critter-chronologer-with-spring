package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        Schedule createdSchedule = scheduleService.createSchedule(schedule, scheduleDTO.getPetIds(), scheduleDTO.getEmployeeIds());

        ScheduleDTO mappedSchedule = modelMapper.map(createdSchedule, ScheduleDTO.class);
        mappedSchedule.setEmployeeIds(createdSchedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        mappedSchedule.setPetIds(createdSchedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));

        return mappedSchedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s : scheduleService.getAllSchedules()) {
            ScheduleDTO scheduleDTO = modelMapper.map(s, ScheduleDTO.class);
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(s.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            scheduleDTOs.add(scheduleDTO);
        }
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s : scheduleService.retrieveScheduleForPet(petId)) {
            ScheduleDTO scheduleDTO = modelMapper.map(s, ScheduleDTO.class);
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(s.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            scheduleDTOs.add(scheduleDTO);
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s : scheduleService.retrieveScheduleForEmployee(employeeId)) {
            ScheduleDTO scheduleDTO = modelMapper.map(s, ScheduleDTO.class);
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(s.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            scheduleDTOs.add(scheduleDTO);
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s : scheduleService.retrieveScheduleForCustomer(customerId)) {
            ScheduleDTO scheduleDTO = modelMapper.map(s, ScheduleDTO.class);
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(s.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            scheduleDTOs.add(scheduleDTO);
        }
        return scheduleDTOs;
    }
}
