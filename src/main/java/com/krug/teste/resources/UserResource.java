package com.krug.teste.resources;

import com.krug.teste.dto.  ProductDTO;
import com.krug.teste.dto.UserDTO;
import com.krug.teste.dto.UserInsertDTO;
import com.krug.teste.services.ProductService;
import com.krug.teste.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    @Autowired
    private UserService service;

@GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
//    parametros: page , size , sort
    Page<UserDTO> list = service.findAllPage(pageable);
        return ResponseEntity.ok().body(list);    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }
@PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserInsertDTO dto){
        UserDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(newDto.getId())
         .toUri();
        return ResponseEntity.created(uri).body(newDto);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete( @PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}