package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="students")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @Column(name = "registration_date")
    private Date registrationDate = new Date();
}
