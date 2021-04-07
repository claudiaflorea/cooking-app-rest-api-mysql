package com.practice.cooking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.practice.cooking.utils.TestUtils.createChef;
import static com.practice.cooking.utils.TestUtils.createDish;
import static com.practice.cooking.utils.TestUtils.createIngredient;
import static com.practice.cooking.utils.TestUtils.createRecipe;
import static com.practice.cooking.utils.TestUtils.createRestaurant;
import static com.practice.cooking.utils.TestUtils.createRestaurantToChefLink;
import static com.practice.cooking.utils.TestUtils.createRestaurantToDishLink;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Dish;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Restaurant;
import com.practice.cooking.model.RestaurantToChef;
import com.practice.cooking.model.RestaurantToDish;
import com.practice.cooking.model.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantRepositoryTest {

    private static final String ALS_SEAFOOD   = "Als Seafood";
    private static final String BON_APPETIT   = "Bon appetit";
    private static final String CHEF_EUGENE   = "Chef. Eugene";
    private static final String CHEF_STAN     = "Chef. Stan";
    private static final String APPLE_PIE     = "Apple pie";
    public static final  String GUACAMOLE     = "Guacamole";
    private static final String RISOTTO     = "Risotto";
    private static final String APPLE = "Apple";
    private static final String FLOUR = "Flour";
    private static final String CINNAMON = "Cinnamon";
    private static final String YEAST = "Yeast";
    private static final String SUGAR = "Sugar";
    private static final String MELTED_BUTTER = "Melted Butter";
    private static final String VEGETABLE_OIL = "Vegetable oil";
    private static final String WATER = "Water";
    private static final String AVOCADO = "Avocado";
    private static final String GARLIC = "Garlic";
    private static final String OLIVE_OIL = "Olive oil";
    private static final String RICE = "Rice";
    private static final String SALT = "Salt";
    private static final String PEPPER = "Pepper";
    private static final String BUTTER = "Butter";

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private RestaurantToChefRepository restaurantToChefRepository;

    @Autowired
    private RestaurantToDishRepository restaurantToDishRepository;
    
    @Autowired
    private ChefRepository chefRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Autowired
    private DishRepository dishRepository;

    @BeforeEach
    void init() {
        
        Chef chefEugene = createChef(CHEF_EUGENE);
        Chef chefStan = createChef(CHEF_STAN);
        chefRepository.saveAll(asList(chefEugene, chefStan));

        Recipe applePie = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        Recipe risotto = createRecipe(RISOTTO, Difficulty.MEDIUM, 1, RecipeType.SIDE);
        Recipe guacamole = createRecipe(GUACAMOLE, Difficulty.EASY, 1, RecipeType.SIDE);
        Recipe applePie2 = createRecipe(APPLE_PIE, Difficulty.EASY, 4, RecipeType.DESSERT);
        recipeRepository.saveAll(asList(applePie, risotto, guacamole, applePie2));

        Set<Ingredient> applePieIngredients = new TreeSet<>();
        applePieIngredients.add(createIngredient(APPLE, 5, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(FLOUR, 2, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(CINNAMON, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(YEAST, 0.001, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(SUGAR, 0.01, Unit.KG, applePie));
        applePieIngredients.add(createIngredient(MELTED_BUTTER, 0.01, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(VEGETABLE_OIL, 0.001, Unit.LITER, applePie));
        applePieIngredients.add(createIngredient(WATER, 0.005, Unit.LITER, applePie));
        ingredientRepository.saveAll(applePieIngredients);

        Set<Ingredient> guacamoleIngredients = new TreeSet<>();
        guacamoleIngredients.add(createIngredient(AVOCADO, 2, Unit.PIECE, guacamole));
        guacamoleIngredients.add(createIngredient(GARLIC, 2, Unit.KG, guacamole));
        guacamoleIngredients.add(createIngredient(OLIVE_OIL, 0.001, Unit.LITER, guacamole));
        ingredientRepository.saveAll(guacamoleIngredients);

        Set<Ingredient> risottoIngredients = new TreeSet<>();
        risottoIngredients.add(createIngredient(RICE, 1, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(SALT, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(PEPPER, 0.001, Unit.KG, risotto));
        risottoIngredients.add(createIngredient(BUTTER, 0.1, Unit.KG, risotto));
        ingredientRepository.saveAll(risottoIngredients);

        Dish applePieDish  = createDish(APPLE_PIE, applePie);
        Dish risottoDish  = createDish(RISOTTO, risotto);
        Dish guacamoleDish  = createDish(GUACAMOLE, guacamole);
        dishRepository.saveAll(asList(applePieDish, risottoDish, guacamoleDish));

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(createRestaurant(ALS_SEAFOOD, 5));
        restaurants.add(createRestaurant(BON_APPETIT, 3));
        restaurantRepository.saveAll(restaurants);

        RestaurantToChef restaurantToChef1 = createRestaurantToChefLink(restaurants.get(0), asSet(chefEugene));
        RestaurantToChef restaurantToChef2 = createRestaurantToChefLink(restaurants.get(1), asSet(chefStan));
        restaurantToChefRepository.saveAll(asList(restaurantToChef1, restaurantToChef2));

        RestaurantToDish restaurantToDish1 = createRestaurantToDishLink(restaurants.get(0), asSet(applePieDish, risottoDish));
        RestaurantToDish restaurantToDish2 = createRestaurantToDishLink(restaurants.get(1), asSet(guacamoleDish));
        restaurantToDishRepository.saveAll(asList(restaurantToDish1, restaurantToDish2));
        
    }

    @AfterEach
    void after() {
        chefRepository.deleteAll();
        dishRepository.deleteAll();
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
        restaurantRepository.deleteAll();
        restaurantToChefRepository.deleteAll();
        restaurantToDishRepository.deleteAll();
    }
    
    @Test
    public void testGetRestaurantsByName() {

        //when
        List<Restaurant> result = restaurantRepository.findAllByName(ALS_SEAFOOD);

        //then
        assertThat(result).hasSize(1);
        assertThat(result).element(0).returns(ALS_SEAFOOD, from(Restaurant::getName));
    }

    @Test
    public void testGetVegetarianRestaurants() {

        //when
        List<Restaurant> result = restaurantRepository.findAllByDishesNotContainingMeat();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).element(0).returns(ALS_SEAFOOD, from(Restaurant::getName));
        assertThat(result).element(1).returns(BON_APPETIT, from(Restaurant::getName));

    }
}
