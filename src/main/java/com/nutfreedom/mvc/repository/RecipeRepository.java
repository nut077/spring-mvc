package com.nutfreedom.mvc.repository;

import com.nutfreedom.mvc.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
