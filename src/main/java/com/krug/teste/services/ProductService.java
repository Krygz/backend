package com.krug.teste.services;

import com.krug.teste.dto.CategoryDTO;
import com.krug.teste.dto.ProductDTO;
import com.krug.teste.model.Category;
import com.krug.teste.model.Product;
import com.krug.teste.repositories.CategoryRepository;
import com.krug.teste.repositories.ProductRepository;
import com.krug.teste.services.exceptions.DatabaseException;
import com.krug.teste.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
@Transactional(readOnly = true)
    public Page<ProductDTO> findAllPage(PageRequest pageRequest){
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }
@Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj  = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity,entity.getCategories());

    }
@Transactional
    public ProductDTO insert(ProductDTO dto) {
         Product entity = new Product();
        copyDtoToEntiry(entity , dto);
       entity = productRepository.save(entity);
          return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id ,ProductDTO dto) {
    try {
        Product entity = productRepository.getReferenceById(id);
        copyDtoToEntiry(entity , dto);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }
    catch (ResourceNotFoundException e ){
          throw new ResourceNotFoundException("Id not found "+id);
    }
    }
    public void delete(Long id) {
    try {
        productRepository.deleteById(id);
    }
    catch (EmptyResultDataAccessException e){
        throw new ResourceNotFoundException("Id not found "+id);
    }
    catch (DataIntegrityViolationException e ){
        throw new DatabaseException("Integrity Violation");
    }
  }
    private void copyDtoToEntiry(Product entity, ProductDTO dto) {
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setImgUrl(dto.getImgUrl());

            entity.getCategories().clear();
            for(CategoryDTO cat : dto.getCategories()){
                Category category = categoryRepository.getReferenceById(cat.getId());
                entity.getCategories().add(category);
            }
    }

}
