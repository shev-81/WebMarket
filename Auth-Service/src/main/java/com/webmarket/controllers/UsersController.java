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
import webmarket.core.OrderDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер определяющий основные Энд-поинты для работы с пользователями.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UsersController {

    /**
     * Сервис пользователей.
     */
    private final UserService userService;

    /**
     * Конвертер в ДТО из сущности и обратно.
     */
    private final UserConverter userConverter;

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
