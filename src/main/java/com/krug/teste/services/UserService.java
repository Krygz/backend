package com.krug.teste.services;

import com.krug.teste.dto.*;
import com.krug.teste.model.User;
import com.krug.teste.repositories.UserRepository;
import com.krug.teste.services.exceptions.DatabaseException;
import com.krug.teste.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

@Transactional(readOnly = true)
    public Page<UserDTO> findAllPage(Pageable pageable){
        Page<User> list = repository.findAll(pageable);
        return list.map(x -> new UserDTO(x));
    }
@Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional<User> obj  = repository.findById(id);
    User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity);

    }
@Transactional
    public UserDTO insert(UserDTO dto) {
    User entity = new User();
        copyDtoToEntity(entity , dto);
       entity = repository.save(entity);
          return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id ,UserDTO dto) {
    try {
        User entity = repository.getReferenceById(id);
        copyDtoToEntity(entity , dto);
        entity = repository.save(entity);
        return new UserDTO(entity);
    }
    catch (ResourceNotFoundException e ){
          throw new ResourceNotFoundException("Id not found "+id);
    }
    }
    public void delete(Long id) {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new DatabaseException("id not found "+id);
        }
  }
    private void copyDtoToEntity(User entity, UserDTO dto) {
          entity.setFirstName(dto.getFirstName());
          entity.setLastName(dto.getLastName());
          entity.setEmail(dto.getEmail());


      }
}
