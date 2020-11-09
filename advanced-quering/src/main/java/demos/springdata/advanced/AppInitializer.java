package demos.springdata.advanced;

import demos.springdata.advanced.entities.Label;
import demos.springdata.advanced.entities.Shampoo;
import demos.springdata.advanced.entities.Size;
import demos.springdata.advanced.repositories.IngredientRepository;
import demos.springdata.advanced.repositories.LabelRepository;
import demos.springdata.advanced.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppInitializer implements CommandLineRunner {

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
    public void run(String... args) throws Exception {
        Page<Shampoo> page = this.shampooRepo.findAll(PageRequest.of(0, 5));
        System.out.printf("Page %d of %d%n-------------------%n", page.getNumber(), page.getTotalPages());
        page.forEach(s -> System.out.printf("%s %s %s%n", s.getBrand(), s.getSize(), s.getPrice()));
        while(page.hasNext()) {
            page = this.shampooRepo.findAll(page.nextPageable());
            System.out.printf("Page %d of %d%n-------------------%n", page.getNumber(), page.getTotalPages());
            page.forEach(s -> System.out.printf("%s %s %s%n", s.getBrand(), s.getSize(), s.getPrice()));
        }

//        1.	Select Shampoos by Size
        this.shampooRepo.findBySize(Size.MEDIUM)
            .forEach(s -> {
                System.out.printf("%s %s %s%n", s.getBrand(), s.getSize(), s.getPrice());
            });
        //2.	Select Shampoos by Size or Label
        Label label = this.labelRepo.findOneById(10L);
        this.shampooRepo
                .findAllBySizeOrLabelOrderByPrice(Size.MEDIUM, label)
                .forEach(s -> {
                    System.out.printf("%s %s %s%n", s.getBrand(), s.getSize(), s.getPrice());
                });

        //3.	Select Shampoos by Price
        this.shampooRepo.findAllByPriceGreaterThanOrderByPriceDesc(7)
            .forEach(s -> {
                System.out.printf("%s %s %s%n", s.getBrand(),s.getSize(), s.getPrice());
            });

        //4.	Select Ingredients by Name
        this.ingredientRepo.findAllByNameStartingWith("M")
                .forEach(i->{
                    System.out.printf("%s%n", i.getName());
                });

        //5.	Select Ingredients by Names
        List<String> list = new ArrayList<>(){{
            add("Lavender");
            add("Herbs");
            add("Apple");
        }};
        this.ingredientRepo.findAllByNameInOrderByPrice(list)
                .forEach(i-> {
                    System.out.printf("%s%n", i.getName());
                });
        //6.	Count Shampoos by Price
        System.out.println(this.shampooRepo.countOfShampooUnderPrice(new BigDecimal("8.5")));

        //JPQL Part
//        7.	Select Shampoos by Ingredients
        this.shampooRepo.findAllWithIngredientsInList(
                List.of(ingredientRepo.findByName("Berry"), ingredientRepo.findByName("Mineral-Collagen")))
            .forEach(x-> System.out.printf("%s%n", x.getBrand()));

        //8.	Select Shampoos by Ingredients Count
        this.shampooRepo.findAllWithCountOfIngredientsLowerThan(2)
                .forEach(x-> System.out.printf("%s%n", x.getBrand()));


    }
}
