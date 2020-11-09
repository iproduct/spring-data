package course.springdata.mapping.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ManagerDto {
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private List<EmployeeDto> employees = new ArrayList<>();
    private String newProp;

    public int  getSubordinatesCount() {
        return employees.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(", ").append(firstName);
        sb.append(" ").append(lastName);
        sb.append(", subordinates: ").append(getSubordinatesCount());
        sb.append("\n").append(employees.stream().map(emp -> emp.toString()).collect(Collectors.joining("\n")));
        return sb.toString();
    }
}
