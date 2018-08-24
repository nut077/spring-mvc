package com.nutfreedom.mvc.service;

import com.nutfreedom.mvc.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeid, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
