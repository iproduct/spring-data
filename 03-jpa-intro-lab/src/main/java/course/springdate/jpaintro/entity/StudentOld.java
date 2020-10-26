package course.springdate.jpaintro.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

//@Entity
@Table(name="students")
public class StudentOld {
    private Long id;
    private String name;
    private Date registrationDate = new Date();

    public StudentOld() {
    }

    public StudentOld(String name) {
        this.name = name;
    }

    public StudentOld(Long id, String name, Date registrationDate) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Column(name = "registration_date")
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentOld)) return false;
        StudentOld student = (StudentOld) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append('}');
        return sb.toString();
    }
}
