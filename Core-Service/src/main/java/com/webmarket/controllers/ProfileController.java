package com.webmarket.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webmarket.core.ProfileDto;

@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
public class ProfileController {
    @GetMapping
    @Operation(
            summary = "Запрос на получение имени пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )
    public ProfileDto getCurrentUserInfo(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        return new ProfileDto(username);
    }
}
