package demos.springdata.advanced.dao;

import demos.springdata.advanced.entity.Ingredient;
import demos.springdata.advanced.entity.Label;
import demos.springdata.advanced.entity.Shampoo;
import demos.springdata.advanced.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findBySize(Size size);
    List<Shampoo> findBySizeOrLabelOrderByPrice(Size size, Label label);
    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(double price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i in :ingredients")
    List<Shampoo> findWithIngredientsInList(@Param("ingredients") Iterable<Ingredient> ingredients);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size <= :count")
    List<Shampoo> findByCountOfIngredientsLowerThan(@Param("count") int count);

}
