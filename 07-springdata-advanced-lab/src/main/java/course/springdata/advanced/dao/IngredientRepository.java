package course.springdata.advanced.dao;

import course.springdata.advanced.entity.Ingredient;
import course.springdata.advanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Shampoo> findBySizeOrderById(String size);
}
