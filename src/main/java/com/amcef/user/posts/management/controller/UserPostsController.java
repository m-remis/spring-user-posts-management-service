package com.amcef.user.posts.management.controller;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.ServerResponse;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.service.ConvertService;
import com.amcef.user.posts.management.service.IntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Michal Remis
 */
@RestController
public class UserPostsController {

    public static final String CONTROLLER_PREFIX = "/user-posts";
    public static final String GET_FOR_USER = "/user-posts/user";

    private final ConvertService convertService;
    private final IntegrationService integrationService;

    public UserPostsController(final ConvertService convertService,
                               final IntegrationService integrationService) {
        this.convertService = convertService;
        this.integrationService = integrationService;
    }

    @PostMapping(CONTROLLER_PREFIX)
    @Operation(description = "Creates user post, checks if user exists in external system")
    ServerResponse<UserPostResponseDto> createUserPost(@RequestParam(value = "userId") Integer userId, @RequestBody @Validated CreateUserPostRequestDto createUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.createPost(convertService.convert(createUserPostRequestDto, userId))));
    }

    @GetMapping(GET_FOR_USER)
    @Operation(description = "Returns posts for user by his id")
    ServerResponse<List<UserPostResponseDto>> getUserPostsByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ServerResponse<>(integrationService.findPostByUserId(userId).stream().map(convertService::convert).toList());
    }

    @GetMapping(CONTROLLER_PREFIX)
    @Operation(description = "Returns post for user, if not found in internal system, searches external system and saves it")
    ServerResponse<UserPostResponseDto> getUserPostsById(@RequestParam(value = "id") Integer id) {
        return new ServerResponse<>(convertService.convert(integrationService.findPostById(id)));
    }

    @PatchMapping(CONTROLLER_PREFIX)
    @Operation(description = "Updates user post")
    ServerResponse<UserPostResponseDto> updateUserPost(@RequestParam(value = "id") Integer id, @RequestBody @Validated UpdateUserPostRequestDto updateUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.updatePost(convertService.convert(updateUserPostRequestDto, id))));
    }

    @DeleteMapping(CONTROLLER_PREFIX)
    @Operation(description = "Removes user post")
    ResponseEntity<Void> deleteUserPost(@RequestParam(value = "id") Integer id) {
        integrationService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
