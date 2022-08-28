package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    @Column(name = "skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Column(name = "dateAvailable")
    private Set<DayOfWeek> daysAvailable;


}
