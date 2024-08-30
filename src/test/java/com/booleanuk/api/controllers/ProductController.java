package com.booleanuk.api.controllers;

import com.booleanuk.api.exceptions.ProductAlreadyExistsException;
import com.booleanuk.api.exceptions.ProductNotFoundException;
import com.booleanuk.api.models.Product;
import com.booleanuk.api.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductRepository theProducts;

    public ProductController(){
        this.theProducts = new ProductRepository();
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        Product newProduct;
        try{
            newProduct = this.theProducts.create(product);
        } catch (ProductAlreadyExistsException e){
            throw new ProductAlreadyExistsException(e.getMessage());
        }
        return newProduct;
    }

    @GetMapping
    public ArrayList<Product> getAll(){
        return this.theProducts.getAll();
    }

    /*@GetMapping("{category}")
    public ArrayList<Product> getCategory(@PathVariable String category){
        return this.theProducts.getCategory(category);
    }*/

    @GetMapping("{id}")
    public Product getOne(@PathVariable int id){
        try {
            return this.theProducts.getOne(id);
        } catch (NoSuchElementException e){
            throw new ProductNotFoundException("No product with that ID found!");
        }
    }

    @PutMapping("{id}")
    public Product update(@PathVariable int id, @RequestBody Product product){
        Product updatedProduct;
        try{
            updatedProduct = this.theProducts.update(id, product);
        } catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return updatedProduct;
    }

    @DeleteMapping("{id}")
    public Product delete(@PathVariable int id){
        try {
            return this.theProducts.delete(id);
        } catch (NoSuchElementException e){
            throw new ProductNotFoundException("No product with that ID found!");
        }
    }
}
