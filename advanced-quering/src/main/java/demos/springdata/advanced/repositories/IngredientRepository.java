package demos.springdata.advanced.repositories;

import demos.springdata.advanced.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

//    @Query(value = "select s from Shampoo AS s join s.ingredients i where i in :ingredients")
//    List<Shampoo> findByIngredientsIn(@Param(value = "ingredients") Set<Ingredient> ingredients);

    List<Ingredient> findAllByNameStartingWith(String s);

    List<Ingredient> findAllByNameInOrderByPrice(List<String> names);

    Ingredient findByName(String name);
}
