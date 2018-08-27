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
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                if (ingredient != null) {
                    ingredient.setRecipe(recipe);
                }
                recipe.addIngredient(ingredient);

            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.orElseThrow(() -> new RuntimeException("Ingredient not found")));
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientid) {
        log.info("Deleting ingredient: " + recipeId + ":" + ingredientid);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            log.info("found recipe");

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientid))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                log.info("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
                recipeRepository.save(recipe);
            }
        } else {
            log.error("Recipe id not found. Id:" + recipeId);
        }
    }
}
