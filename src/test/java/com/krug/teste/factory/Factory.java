package com.krug.teste.factory;

import com.krug.teste.dto.ProductDTO;
import com.krug.teste.model.Category;
import com.krug.teste.model.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(
                1L
                ,"Phone"
                ,"Phone God"
                ,800.0
                ,"http://img.png"
                , Instant.now()
        );
       product.getCategories().add(new Category(2L , "Electronics"));
       return product;
    }

    public static ProductDTO createProductDto(){
        Product product = createProduct();
        return new ProductDTO(product , product.getCategories());
    }

}
