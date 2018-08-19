package com.nutfreedom.mvc.service.impl;

import com.nutfreedom.mvc.command.IngredientCommand;
import com.nutfreedom.mvc.converter.IngredientToIngredientCommand;
import com.nutfreedom.mvc.entity.Recipe;
import com.nutfreedom.mvc.repository.RecipeRepository;
import com.nutfreedom.mvc.service.IngredientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeid, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeid);

        if (!recipeOptional.isPresent()) {
            log.error("recipe id not found Id: " + recipeid);
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .map(ingredientToIngredientCommand::convert).findFirst();

            if (!ingredientCommandOptional.isPresent()) {
                log.error("Ingredient id not found: " + ingredientId);
            } else {
                return ingredientCommandOptional.get();
            }
        }

        return null;
    }
}
