package se.seqarc.samplersequencer.categoryTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.seqarc.samplersequencer.category.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void testGetCategoryByCategory() throws CategoryNotFoundException {
        // GIVEN
        Category bassDrum = new Category();
        bassDrum.setCategory("Bass Drum");
        bassDrum.setId(1L);
        when(categoryRepository.findCategoryByCategory("Bass Drum"))
                .thenReturn(java.util.Optional.of(bassDrum));

        // WHEN
        CategoryDTO categoryDTO = categoryService.getCategoryByCategory("Bass Drum");

        // THEN
        Assert.assertEquals(categoryDTO.getCategory(), bassDrum.getCategory());
    }

    @Test
    public void testFindAllCategories() {
        // GIVEN
        Category bassDrum = new Category();
        bassDrum.setCategory("Bass Drum");
        bassDrum.setId(1L);
        Category snare= new Category();
        snare.setCategory("Snare");
        snare.setId(1L);
        Category clap = new Category();
        clap.setCategory("Clap");
        clap.setId(1L);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(bassDrum);
        categoryList.add(snare);
        categoryList.add(clap);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        // WHEN
        List<CategoryDTO> found = categoryService.getAllCategories();

        // THEN
        Assert.assertEquals(categoryList.get(0).getCategory(), found.get(0).getCategory());
    }
}


