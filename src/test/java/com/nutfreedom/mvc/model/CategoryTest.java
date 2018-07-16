package com.nutfreedom.mvc.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    private Category category;
    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long id = 5L;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
        String des = "des";
        category.setDescription(des);
        assertEquals(des, category.getDescription());
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        category.setRecipes(recipes);
        assertEquals(1, category.getRecipes().size());
    }
}