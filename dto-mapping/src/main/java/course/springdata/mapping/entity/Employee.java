package course.springdata.mapping.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private double salary;
    @NonNull
    private LocalDate birthday;
    private boolean onVacation = false;
    @ManyToOne(optional = true)
    private Employee manager;
    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Employee> subordinates = new ArrayList<>();

    @NonNull
    @ManyToOne
    private Address address;

    public Employee(@NonNull String firstName, @NonNull String lastName, @NonNull double salary,
                    @NonNull LocalDate birthday, Address address, Employee manager) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
        this.manager = manager;
    }
}
