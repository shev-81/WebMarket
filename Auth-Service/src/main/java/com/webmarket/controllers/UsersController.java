package com.webmarket.controllers;


import com.webmarket.converters.UserConverter;
import com.webmarket.entities.User;
import com.webmarket.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webmarket.auth.UserDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The controller defines the main End points for working with users.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UsersController {

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Converter to DTO from the entity and back.
     */
    private final UserConverter userConverter;


    /**
     * Request to get a list of users.
     * http://localhost:5555/api/v1/user
     * @return
     */
    @GetMapping
    @Operation(
            summary = "Запрос на получение списка пользователей",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )
                    )
            }
    )
    public List<UserDto> allUsers() {
        return userService.getAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }


    /**
     * Request to receive the user's DTO by his name.
     * http://localhost:5555/api/v1/user/{userName}
     * @param userName
     * @return
     */
    @GetMapping("/{userName}")
    @Operation(
            summary = "Запрос на получение ДТО пользователя по его имени.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    )
            }
    )
    public UserDto findUserByName(@PathVariable String userName) {
        return userService.findByUsername(userName).map(userConverter::entityToDto).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }


    /**
     * Request to create a new user.
     * http://localhost:5555/api/v1/new
     * @param userDto
     */
    @PostMapping("/new")
    @Operation(
            summary = "Запрос на создание нового пользователя.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody UserDto userDto){
        User newUser = userConverter.dtoToEntity(userDto);
        userService.createNewUser(newUser, userDto.getRoles());
    }

    /**
     * Request to delete a user by his ID.
     * http://localhost:5555/api/v1/{id}
     * @param id
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Запрос на удаление пользователя по его ID.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    )
            }
    )
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }
}
