package com.example.ermfiap.domain.Users.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ermfiap.domain.Users.entity.Users;
import com.example.ermfiap.domain.Users.repository.UsersSprigDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
public class UserController {
    private UsersSprigDataRepository repository;

    public UserController(UsersSprigDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<Users> findAll(@RequestHeader(value="x-access-token") String token) {
        return repository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<Users> signUp(@RequestHeader(value="x-access-token") String token, @RequestBody Users user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        if (token.equals("secret-key-to-adm")) {
            user.setType("adm");
            Users createdUser = repository.save(user);
            if (createdUser != null) {
                URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
                return ResponseEntity.created(newLocation).build();
            }
        }

        String email = JWT.decode(token).getIssuer();
        Optional<Users> searchUser = repository.findByEmailEquals(email);
        if (searchUser.isEmpty() || !searchUser.get().getType().equals("adm")) {
            return ResponseEntity.status(401).build();
        }

        Users createdUser = repository.save(user);
        if (createdUser != null) {
            URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
            return ResponseEntity.created(newLocation).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("users/login")
    public ResponseEntity<Users> login(@RequestBody Users user) {
        System.out.println("HELLO: " + user);
        Optional<Users> searchUser = repository.findByEmailEquals(user.getEmail());

        if(searchUser.isPresent()) {
            if(BCrypt.checkpw(user.getPassword(), searchUser.get().getPassword())) {
                String jwtToken = JWT.create().withIssuer(user.getEmail()).sign(Algorithm.HMAC256("MySecret"));
                searchUser.get().setTokenAccess(jwtToken);
                Users loggedUser = repository.save(searchUser.get());
                return ResponseEntity.ok(loggedUser);
            }
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/logout")
    public ResponseEntity<String> logout(@RequestBody Users user) {
        DecodedJWT decodedObj = JWT.decode(user.getTokenAccess());
        Optional<Users> searchUser = repository.findByEmailEquals(decodedObj.getIssuer());
        searchUser.get().setTokenAccess(null);
        repository.save(searchUser.get());
        System.out.println("aqui: " + searchUser.get().toString());
        return ResponseEntity.ok("Success Logout");
    }
}