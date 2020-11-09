package course.springdata.mapping.dto;

import course.springdata.mapping.entity.Address;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private double salary;
    private String managerLastName;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("  - id: ").append(id);
        sb.append(", ").append(firstName);
        sb.append(" ").append(lastName);
        sb.append(", salary=").append(salary);
        sb.append(", manager=").append(managerLastName);
        return sb.toString();
    }
}
