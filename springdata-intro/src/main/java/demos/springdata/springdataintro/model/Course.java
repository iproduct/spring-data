package demos.springdata.springdataintro.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    @NonNull
    private String name;
    private List<Course> courses = new ArrayList<>();
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime modified;
    @ManyToMany()
    @JoinTable(name="COURSE_STUDENT",
            joinColumns=
            @JoinColumn(name="COURSE_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="STUDENT_ID", referencedColumnName="ID")
    )
    private List<Student> students = new ArrayList<>();
}
