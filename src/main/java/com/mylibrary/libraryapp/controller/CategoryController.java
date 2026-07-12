package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.CategoryRequest;
import com.mylibrary.libraryapp.dto.response.CategoryResponse;
import com.mylibrary.libraryapp.exceptions.DuplicateResourceException;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import com.mylibrary.libraryapp.repositories.CategoryRepository;
import com.mylibrary.libraryapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping()
    public ResponseEntity<MessageResponse<List<CategoryResponse>>> getAllCategories() {

        List<CategoryResponse> categoryResponse = categoryService.findAll();

        MessageResponse response = new MessageResponse<>(

                categoryResponse, 200, "Success", "All Categories"

        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{name}")
    public ResponseEntity<List<CategoryResponse>> getCategoryByName(@PathVariable String name) {

        List<CategoryResponse> response = categoryService.findByName(name);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MessageResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse request = categoryService.createCategory(categoryRequest);

       MessageResponse response = new MessageResponse(
               request,
                200, "Success", "Category Created"
       );

       return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<CategoryResponse>> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,@PathVariable("id") Long id) {
        CategoryResponse category = categoryService.updateCategory(id, categoryRequest);
        MessageResponse response = new MessageResponse(category, 200, "Success", "Category Updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }
}
