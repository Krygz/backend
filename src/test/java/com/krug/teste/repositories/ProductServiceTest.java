package com.krug.teste.repositories;

import com.krug.teste.dto.ProductDTO;
import com.krug.teste.model.Product;
import com.krug.teste.resources.ProductResource;
import com.krug.teste.services.ProductService;
import com.krug.teste.services.exceptions.ResourceNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;
    private Long nonExistingId ;
    private Long existingId;
    private Long countTotalProducts;
    private ProductDTO product;
    private ProductDTO product2;

    @BeforeEach
    void setUp() throws  Exception{
        product = new ProductDTO(29L ,
                "Electronics",
                "do that",
                900.0,
                "http://img.com",
                Instant.now());
        product2 = new ProductDTO(19L ,
                "Electronics",
                "do that",
                900.0,
                "http://img.com",
                Instant.now());
       existingId = 2L;
       nonExistingId = 1000L;
       countTotalProducts = 25L;
    }
@Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts - 1 , repository.count());
    }
@Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }
@Test
    public void findByIdShouldExistsWhenProductExist(){
        service.findById(existingId);
        Assertions.assertEquals(2L , existingId);
    }
    @Test
    public void findByIDShouldNotExistWhenProductNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class , () -> {
            service.findById(nonExistingId);
        });
    }
@Test
    public void insertShouldBeTrueWhenIdNotExist(){
        service.insert(product);
        Assertions.assertEquals(29L, product.getId());
    }
    @Test
    public void insertShouldBeFalseWhenIdExists(){
        service.insert(product2);
        Assertions.assertEquals(19 , product2.getId());
    }
@Test
    public void findAllPagedShouldReturnPageWhenPage0Size10(){

        PageRequest request = PageRequest.of(0 , 10);

        Page<ProductDTO> result = service.findAllPage(request);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0 , result.getNumber());
        Assertions.assertEquals(10 , result.getSize());
        Assertions.assertEquals(countTotalProducts , result.getTotalElements());
    }
    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist(){

        PageRequest request = PageRequest.of(50 , 10);

        Page<ProductDTO> result = service.findAllPage(request);

        Assertions.assertTrue(result.isEmpty());
    }
    @Test
    public void findAllPagedShouldReturnSortedPageWhenSortedByName(){

        PageRequest request = PageRequest.of(0,10, Sort.by("name"));

        Page<ProductDTO> result = service.findAllPage(request);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro" ,result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer" ,result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa" ,result.getContent().get(2).getName());
    }



}
