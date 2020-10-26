package course.springdata.entities;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    @NonNull
    private String name;
    private Date registrationDate = new Date();
}
