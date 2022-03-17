package _03.Entities;

import javax.persistence.*;
import java.util.Set;

//Student (id, first name, last name, phone number, average grade, attendance)
@Entity
@Table(name = "_03_students")
public class Student extends Person{


    @Column(name = "average_grade", nullable = false)
    private double averageGrade;

    private int attendance;

    @ManyToMany
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "students_id" ,referencedColumnName =  "id"),
    inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName =  "id"))
    private Set<Course> courses;

}
