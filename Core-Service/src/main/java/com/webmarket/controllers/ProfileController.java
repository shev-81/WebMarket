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

/**
 * A controller for working with users.
 */
@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Users", description = "Methods of working with users")
public class ProfileController {

    /**
     * Request to get a username.
     * http://localhost:5555/core/api/v1/profile
     * @param username
     * @return
     */
    @GetMapping
    @Operation(
            summary = "Request for a username",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )
    public ProfileDto getCurrentUserInfo(@RequestHeader @Parameter(description = "Username", required = true) String username) {
        return new ProfileDto(username);
    }
}
