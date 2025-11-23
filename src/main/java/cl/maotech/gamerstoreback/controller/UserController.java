package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.SwaggerMessages;
import cl.maotech.gamerstoreback.constant.UserEndpoints;
import cl.maotech.gamerstoreback.dto.MessageResponse;
import cl.maotech.gamerstoreback.dto.UserResponseDto;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserEndpoints.BASE)
@RequiredArgsConstructor
@Tag(name = SwaggerMessages.User.TAG_NAME, description = SwaggerMessages.User.TAG_DESCRIPTION)
public class UserController {

    private final UserService userService;

    @Operation(
            summary = SwaggerMessages.User.GET_ALL_SUMMARY,
            description = SwaggerMessages.User.GET_ALL_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = SwaggerMessages.User.GET_BY_ID_SUMMARY,
            description = SwaggerMessages.User.GET_BY_ID_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping(UserEndpoints.ID)
    public ResponseEntity<UserResponseDto> getUserById(
            @Parameter(description = SwaggerMessages.User.ID_PARAM_DESCRIPTION, example = SwaggerMessages.User.ID_PARAM_EXAMPLE) @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = SwaggerMessages.User.CREATE_SUMMARY,
            description = SwaggerMessages.User.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerMessages.Response.CREATED_201),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @Operation(
            summary = SwaggerMessages.User.UPDATE_SUMMARY,
            description = SwaggerMessages.User.UPDATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @PutMapping(UserEndpoints.ID)
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = SwaggerMessages.User.ID_PARAM_DESCRIPTION, example = SwaggerMessages.User.ID_PARAM_EXAMPLE) @PathVariable Long id,
            @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Operation(
            summary = SwaggerMessages.User.DELETE_SUMMARY,
            description = SwaggerMessages.User.DELETE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @DeleteMapping(UserEndpoints.ID)
    public ResponseEntity<MessageResponse> deleteUser(
            @Parameter(description = SwaggerMessages.User.ID_PARAM_DESCRIPTION, example = SwaggerMessages.User.ID_PARAM_EXAMPLE) @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse(Messages.User.DELETED, HttpStatus.OK.value()));
    }

}
