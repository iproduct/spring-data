package course.springdata.mapping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private double salary;
    private LocalDate birthday;
    private String city;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("  - ").append(id);
        sb.append(": ").append(firstName);
        sb.append(" ").append(lastName);
        sb.append(", salary: ").append(salary);
//        sb.append(", birthday=").append(birthday);
        sb.append(", City: ").append(city).append('\'');
        return sb.toString();
    }
}
