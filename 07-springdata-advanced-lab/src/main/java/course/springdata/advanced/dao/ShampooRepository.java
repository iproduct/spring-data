package course.springdata.advanced.dao;

import course.springdata.advanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
}
