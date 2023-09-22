package com.example.ermfiap.domain.Users.controller;

import com.example.ermfiap.domain.Users.entity.Users;
import com.example.ermfiap.domain.Users.repository.UsersSprigDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private UsersSprigDataRepository repository;

    public UserController(UsersSprigDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Users> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        Users createdUser = repository.save(user);

        URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(newLocation).build();
    }
}