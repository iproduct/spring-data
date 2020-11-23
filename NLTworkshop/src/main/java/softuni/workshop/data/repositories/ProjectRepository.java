package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
