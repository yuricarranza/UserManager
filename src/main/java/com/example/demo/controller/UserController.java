package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository _userRepository;
    public UserController(UserRepository userRepository)
    {
        _userRepository = userRepository;
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        var users = _userRepository.GetUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        var user = _userRepository.GetUserById(id);
        if (user.isPresent()) return ResponseEntity.ok(user.get());
        else return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        var userIdGenerated = _userRepository.CreateUser(user);
        user.setId(userIdGenerated);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userIdGenerated)
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user){
        _userRepository.UpdateUser(id, user);
        return ResponseEntity.ok().build();
    }
}
