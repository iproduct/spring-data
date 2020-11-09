package course.springdata.mapping.dto;

import course.springdata.mapping.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<EmployeeDto> subordinates;

    private int getSubordinatesNumber() { // Derived property
        return subordinates.size();
    }
}
