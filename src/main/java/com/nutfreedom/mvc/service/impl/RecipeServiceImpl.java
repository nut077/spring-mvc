package com.nutfreedom.mvc.service.impl;

import com.nutfreedom.mvc.command.RecipeCommand;
import com.nutfreedom.mvc.converter.RecipeCommandToRecipe;
import com.nutfreedom.mvc.converter.RecipeToRecipeCommand;
import com.nutfreedom.mvc.entity.Recipe;
import com.nutfreedom.mvc.exceptions.NotFoundException;
import com.nutfreedom.mvc.repository.RecipeRepository;
import com.nutfreedom.mvc.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new LinkedHashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (!recipe.isPresent()) {
            throw new NotFoundException("Recipe is not found for ID value: " + id);
        }
        return recipe.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe datachedRecipe = recipeCommandToRecipe.convert(command);
        assert datachedRecipe != null;
        Recipe savedRecipe = recipeRepository.save(datachedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
