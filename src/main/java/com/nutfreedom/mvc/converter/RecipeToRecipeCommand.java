package com.nutfreedom.mvc.converter;

import com.nutfreedom.mvc.command.RecipeCommand;
import com.nutfreedom.mvc.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private CategoryToCategoryCommand categoryConverter;
    private IngredientToIngredientCommand ingredientConverter;
    private NotesToNotesCommand notesConverter;

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }
        final RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setImage(source.getImage());
        command.setNotes(notesConverter.convert(source.getNotes()));
        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(category -> command.getCategories().add(categoryConverter.convert(category)));
        }
        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
