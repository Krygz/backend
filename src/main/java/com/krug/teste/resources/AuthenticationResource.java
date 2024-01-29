package com.krug.teste.resources;

import com.krug.teste.dto.EmailResponseDTO;
import com.krug.teste.dto.UserDTO;
import com.krug.teste.model.User;
import com.krug.teste.repositories.UserRepository;
import com.krug.teste.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationResource {
@Autowired
  private TokenService tokenService;

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private UserRepository repository;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody @Valid UserDTO dto){
     var emailPassword = new UsernamePasswordAuthenticationToken(dto.getEmail() , dto.getPassword());
     var auth = authenticationManager.authenticate(emailPassword);
     var token = tokenService.generateToken((User) auth.getPrincipal());
     return ResponseEntity.ok(new EmailResponseDTO(token));
}
@PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid UserDTO dto){
    if(repository.findByEmail(dto.getEmail()) != null) return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
    User createdUser = new User(dto.getFirstName() , dto.getLastName() , dto.getEmail() , encryptedPassword,  dto.getRole());
    repository.save(createdUser);
    return ResponseEntity.ok().build();
}
}
