package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Category;
import kr.co.fastcompus.eatgo.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
}
