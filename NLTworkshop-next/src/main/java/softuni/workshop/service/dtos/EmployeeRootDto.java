package softuni.workshop.service.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeRootDto {

    @XmlElement(name = "employee")
    private List<EmployeeDto> employeeDtos;

    public EmployeeRootDto() {
    }

    public EmployeeRootDto(List<EmployeeDto> employeeDtos) {
        this.employeeDtos = employeeDtos;
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtos;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtos = employeeDtoList;
    }
}
