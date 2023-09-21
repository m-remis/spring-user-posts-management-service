package com.michal.examples.user.posts.management.controller;

import com.michal.examples.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.michal.examples.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.michal.examples.user.posts.management.dto.response.ServerResponse;
import com.michal.examples.user.posts.management.dto.response.UserPostResponseDto;
import com.michal.examples.user.posts.management.service.ConvertService;
import com.michal.examples.user.posts.management.service.IntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Michal Remis
 */
@RestController
@Tag(name = "User posts")
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
    @Operation(summary = "Creates user post, checks if user exists in external system")
    public ServerResponse<UserPostResponseDto> createUserPost(@RequestParam(value = "userId") Integer userId,
                                                              @RequestBody @Validated CreateUserPostRequestDto createUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.createPost(convertService.convert(createUserPostRequestDto, userId))));
    }

    @GetMapping(GET_FOR_USER)
    @Operation(summary = "Returns posts for user by his user id")
    public ServerResponse<List<UserPostResponseDto>> getUserPostsByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ServerResponse<>(integrationService.findPostByUserId(userId).stream().map(convertService::convert).toList());
    }

    @GetMapping(CONTROLLER_PREFIX)
    @Operation(summary = "Returns post for user by post id, if not found in internal system, searches external system and saves it")
    public ServerResponse<UserPostResponseDto> getUserPostsById(@RequestParam(value = "id") Integer id) {
        return new ServerResponse<>(convertService.convert(integrationService.findPostById(id)));
    }

    @PatchMapping(CONTROLLER_PREFIX)
    @Operation(summary = "Updates user post for post id")
    public ServerResponse<UserPostResponseDto> updateUserPost(@RequestParam(value = "id") Integer id,
                                                              @RequestBody @Validated UpdateUserPostRequestDto updateUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.updatePost(convertService.convert(updateUserPostRequestDto, id))));
    }

    @DeleteMapping(CONTROLLER_PREFIX)
    @Operation(summary = "Removes user post")
    public ResponseEntity<Void> deleteUserPost(@RequestParam(value = "id") Integer id) {
        integrationService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
