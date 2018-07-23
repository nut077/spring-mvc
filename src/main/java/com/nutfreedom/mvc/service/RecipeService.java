package com.nutfreedom.mvc.service;

import com.nutfreedom.mvc.entity.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
}
