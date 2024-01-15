package com.krug.teste.services;

import com.krug.teste.dto.CategoryDTO;
import com.krug.teste.dto.ProductDTO;
import com.krug.teste.model.Category;
import com.krug.teste.model.Product;
import com.krug.teste.repositories.CategoryRepository;
import com.krug.teste.repositories.ProductRepository;
import com.krug.teste.services.exceptions.DatabaseException;
import com.krug.teste.services.exceptions.ResourceNotFoundException;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPage(Pageable pageable){
        Page<Category> list = categoryRepository.findAll(pageable);
        return list.map(x -> new CategoryDTO(x));
    }
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Optional<Category> obj  = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity);

    }
    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO update(Long id ,CategoryDTO dto) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        }
        catch (ResourceNotFoundException e ){
            throw new ResourceNotFoundException("Id not found "+id);
        }
    }
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found "+id);
        }
        catch (DataIntegrityViolationException e ){
            throw new DatabaseException("Integrity Violation");
        }
    }
}
