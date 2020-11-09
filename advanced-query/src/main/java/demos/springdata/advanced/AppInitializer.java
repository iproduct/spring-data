package demos.springdata.advanced;

import demos.springdata.advanced.dao.IngredientRepository;
import demos.springdata.advanced.dao.LabelRepository;
import demos.springdata.advanced.dao.ShampooRepository;
import demos.springdata.advanced.entity.Ingredient;
import demos.springdata.advanced.entity.Label;
import demos.springdata.advanced.entity.Shampoo;
import demos.springdata.advanced.entity.Size;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static demos.springdata.advanced.entity.Size.MEDIUM;

@Component
public class AppInitializer implements ApplicationRunner {
    private final ShampooRepository shampooRepo;
    private final LabelRepository labelRepo;
    private final IngredientRepository ingredientRepo;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepo, LabelRepository labelRepo, IngredientRepository ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.labelRepo = labelRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        1. Shampoos by size
        shampooRepo.findBySize(MEDIUM)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));

//        2. Shampoos by size or label ordered by price
        Label label = labelRepo.findOneById(10L);
        shampooRepo.findBySizeOrLabelOrderByPrice(MEDIUM, label)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));

//        3. Shampoos priced greater then, ordered by price descending
        shampooRepo.findByPriceGreaterThanOrderByPriceDesc(7)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));

//        4. Shampoos with ingredients in list
        shampooRepo.findWithIngredientsInList(
                List.of(ingredientRepo.findByName("Berry"), ingredientRepo.findByName("Mineral-Collagen")))
                .forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                    s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                    s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

//        5. Ingredients by names in list
        ingredientRepo.findByNameInOrderByPrice(List.of("Lavender", "Herbs", "Apple"))
                .forEach(i -> System.out.printf("%s %s %.2f%n",
                        i.getId(), i.getName(), i.getPrice()));


//        6. Shampoos with ingredients in list
        shampooRepo.findByCountOfIngredientsLowerThan(2)
            .forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

//    7. Update prices by 10 percent
        ingredientRepo.findByNameInOrderByPrice(List.of("Lavender", "Herbs", "Apple"))
                .forEach(i -> System.out.printf("%s %s %.2f%n",
                        i.getId(), i.getName(), i.getPrice()));
        System.out.println("-------- AFTER UPDATE -------");
        System.out.printf("Updated records: %d%n",
                ingredientRepo.updatePriceIngredientsInListBy10Percent(List.of("Lavender", "Herbs", "Apple")));
        ingredientRepo.findAll()
                .forEach(i -> System.out.printf("%s %s %.2f%n",
                        i.getId(), i.getName(), i.getPrice()));

        //8. Print in pages of 5
        Page<Shampoo> page;
        Pageable pageable = PageRequest.of(0,5);
        do {
            page = shampooRepo.findAll(pageable);
            System.out.printf("Page %d of %d:%n------------------%n", page.getNumber()+1, page.getTotalPages());
            page.forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                    s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                    s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));
            pageable = pageable.next();
        } while(page.hasNext());
    }
}
