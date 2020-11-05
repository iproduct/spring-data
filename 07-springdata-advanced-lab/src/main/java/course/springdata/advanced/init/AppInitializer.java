package course.springdata.advanced.init;

import course.springdata.advanced.dao.IngredientRepository;
import course.springdata.advanced.dao.LabelRepository;
import course.springdata.advanced.dao.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
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

    }
}
