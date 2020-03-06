package demos.springdata.advanced.dao;

import demos.springdata.advanced.entity.Label;
import demos.springdata.advanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findOneById(Long id);
}
