package _03.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

//Course (id, name, description, start date, end date, credits)
@Entity
@Table(name = "_03_courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Basic(optional = false)
    private int credits;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    @ManyToOne
    private Teacher teacher;
}
