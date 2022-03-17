package _03.Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

//Teacher (id, first name, last name, phone number, email, salary per hour)
@Entity
@Table(name = "_03_teachers")
public class Teacher extends Person{


    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "salary_per_hour", nullable = false)
    private BigDecimal salaryPerHour;

//    @OneToMany
//    @JoinColumn(referencedColumnName = "id")
//    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;
}
