package com.krug.teste.services;

import com.krug.teste.dto.CategoryDTO;
import com.krug.teste.model.Category;
import com.krug.teste.repositories.CategoryRepository;
import com.krug.teste.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
@Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).toList();
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
}
