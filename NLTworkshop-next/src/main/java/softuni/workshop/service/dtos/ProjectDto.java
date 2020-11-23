package softuni.workshop.service.dtos;

import softuni.workshop.data.entities.Company;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDto {

    private String name;
    private String description;
    @XmlElement(name = "is-finished")
    private boolean isFinished;
    private BigDecimal payment;
    @XmlElement(name = "start-date")
    private String startDate;
    private CompanyDto company;
    public ProjectDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectDto)) return false;
        ProjectDto that = (ProjectDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, company);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProjectDto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", isFinished=").append(isFinished);
        sb.append(", payment=").append(payment);
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", company=").append(company);
        sb.append('}');
        return sb.toString();
    }
}
