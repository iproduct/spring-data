package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByNameAndCompanyAndStartDate(String name, Company company, String startDate);

    List<Project> findAllByFinished(boolean b);
}
