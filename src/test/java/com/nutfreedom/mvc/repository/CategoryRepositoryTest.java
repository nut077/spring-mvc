package com.nutfreedom.mvc.repository;

import com.nutfreedom.mvc.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByDescription() {
        Optional<Category> category = categoryRepository.findByDescription("Mexican");
        assertEquals("Mexican", category.orElse(new Category()).getDescription());
    }
}