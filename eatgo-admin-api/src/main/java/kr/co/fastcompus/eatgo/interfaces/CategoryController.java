package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.CategoryService;
import kr.co.fastcompus.eatgo.domain.Category;
import kr.co.fastcompus.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list(){

        List<Category> regions = categoryService.getCategories();
        return regions;
    }

    @PostMapping("/categories")
    public ResponseEntity<?> create( @RequestBody @Valid Category category) throws URISyntaxException {
        categoryService.addCategory(category);
        URI location = new URI("/categories/" + category.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
