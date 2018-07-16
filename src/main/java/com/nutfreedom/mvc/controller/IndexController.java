package com.nutfreedom.mvc.controller;

import com.nutfreedom.mvc.model.Category;
import com.nutfreedom.mvc.model.UnitOfMeasure;
import com.nutfreedom.mvc.repository.CategoryRepository;
import com.nutfreedom.mvc.repository.UnitOfMeasureRepository;
import com.nutfreedom.mvc.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @GetMapping("/category")
    public String getIdCategoryByDescription(@RequestParam("description") String description, Model model) {
        Optional<Category> category = categoryRepository.findByDescription(description);
        model.addAttribute("category", category.orElse(new Category()).getId());
        return "category";
    }

    @GetMapping("/uom/{description}")
    public String getIdUnitOfMeasureByDescription(@PathVariable String description, Model model) {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(description);
        model.addAttribute("uom", unitOfMeasure.orElse(new UnitOfMeasure()).getId());
        return "uom";
    }


}
