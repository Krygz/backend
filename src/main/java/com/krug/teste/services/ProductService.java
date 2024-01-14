package com.krug.teste.services;

import com.krug.teste.dto.ProductDTO;
import com.krug.teste.model.Product;
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
//       entity.setName(dto.getName());
       entity = productRepository.save(entity);
          return new ProductDTO(entity);
    }
@Transactional
    public ProductDTO update(Long id ,ProductDTO dto) {
    try {
        Product entity = productRepository.getReferenceById(id);
//        entity.setName(dto.getName());
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
}
