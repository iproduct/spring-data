package demos.springdata.advanced.repositories;

import demos.springdata.advanced.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findOneById(Long id);
}
