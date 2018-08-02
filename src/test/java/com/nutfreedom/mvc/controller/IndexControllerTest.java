package com.nutfreedom.mvc.controller;

import com.nutfreedom.mvc.entity.Category;
import com.nutfreedom.mvc.entity.Recipe;
import com.nutfreedom.mvc.entity.UnitOfMeasure;
import com.nutfreedom.mvc.repository.CategoryRepository;
import com.nutfreedom.mvc.repository.UnitOfMeasureRepository;
import com.nutfreedom.mvc.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {
    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IndexController controller;

    public IndexControllerTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void getIndex() {
        Set<Recipe> recipes = new LinkedHashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.getIndex(model);
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void getIdCategoryByDescription() {
        String desc = "American";
        String viewName = controller.getIdCategoryByDescription(desc, model);
        assertEquals("category", viewName);
        Category category = new Category();
        category.setId(1L);
        category.setDescription(desc);
        Optional<Category> categoryOptional = Optional.of(category);
        when(categoryRepository.findByDescription(desc)).thenReturn(categoryOptional);
        assertEquals(desc, categoryRepository.findByDescription(desc).orElse(new Category()).getDescription());
    }

    @Test
    public void getIdUnitOfMeasureByDescription() {
        String desc = "Tablespoon";
        String viewName = controller.getIdUnitOfMeasureByDescription(desc, model);
        assertEquals("uom", viewName);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasure.setDescription(desc);
        Optional<UnitOfMeasure> ofMeasure = Optional.of(unitOfMeasure);
        when(unitOfMeasureRepository.findByDescription(desc)).thenReturn(ofMeasure);
        assertEquals(desc, unitOfMeasureRepository.findByDescription(desc).orElse(new UnitOfMeasure()).getDescription());
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

}