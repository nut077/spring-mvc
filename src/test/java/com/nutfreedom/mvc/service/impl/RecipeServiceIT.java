package com.nutfreedom.mvc.service.impl;

import com.nutfreedom.mvc.command.RecipeCommand;
import com.nutfreedom.mvc.converter.RecipeCommandToRecipe;
import com.nutfreedom.mvc.converter.RecipeToRecipeCommand;
import com.nutfreedom.mvc.entity.Recipe;
import com.nutfreedom.mvc.repository.RecipeRepository;
import com.nutfreedom.mvc.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipes = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipes);

        //when
        assertNotNull(testRecipeCommand);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipes.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipes.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipes.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
