package com.webmarket.controllers;


import com.exemple.spring.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UsersController {

    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> allUsers() {
        return userService.getAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{userName}")
//    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findUserByName(@PathVariable String userName) {
        return userService.findByUsername(userName).map(userConverter::entityToDto).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @PostMapping("/new")
//    @PreAuthorize("hasRole('ADMIN')")
    public void createNewUser(@RequestBody UserDto userDto){
        User newUser = userConverter.dtoToEntity(userDto);
        userService.createNewUser(newUser, userDto.getRoles());
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void delProducts(@PathVariable Long id){
        userService.deleteById(id);
    }
}
