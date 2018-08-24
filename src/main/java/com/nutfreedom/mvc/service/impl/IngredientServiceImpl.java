package com.nutfreedom.mvc.service.impl;

import com.nutfreedom.mvc.command.IngredientCommand;
import com.nutfreedom.mvc.converter.IngredientCommandToIngredient;
import com.nutfreedom.mvc.converter.IngredientToIngredientCommand;
import com.nutfreedom.mvc.entity.Ingredient;
import com.nutfreedom.mvc.entity.Recipe;
import com.nutfreedom.mvc.repository.RecipeRepository;
import com.nutfreedom.mvc.repository.UnitOfMeasureRepository;
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
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found")));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe saveRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(saveRecipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(command.getId()))
            .findFirst().orElseThrow(() -> new RuntimeException("Ingredient not found")));
        }
    }
}
