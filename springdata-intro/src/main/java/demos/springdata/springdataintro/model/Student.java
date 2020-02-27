package demos.springdata.springdataintro.model;

import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    @NonNull
    private String name;
    private Date registrationDate = new Date();
    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();
}
