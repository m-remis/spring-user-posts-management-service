package com.amcef.user.posts.management.controller;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.ServerResponse;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.service.ConvertService;
import com.amcef.user.posts.management.service.IntegrationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amcef.user.posts.management.controller.UserPostsController.CONTROLLER_PREFIX;

/**
 * @author Michal Remis
 */
@RestController(CONTROLLER_PREFIX)
@Validated
public class UserPostsController {

    public static final String CONTROLLER_PREFIX = "/user-posts";

    private final ConvertService convertService;
    private final IntegrationService integrationService;

    public UserPostsController(final ConvertService convertService,
                               final IntegrationService integrationService) {
        this.convertService = convertService;
        this.integrationService = integrationService;
    }

    @PostMapping
    ServerResponse<UserPostResponseDto> createUserPost(@RequestParam(value = "userId") Integer userId, @RequestBody CreateUserPostRequestDto createUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.uploadPost(convertService.convert(createUserPostRequestDto, userId))));
    }

    @GetMapping
    ServerResponse<List<UserPostResponseDto>> getUserPostsByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ServerResponse<>(integrationService.findPostByUserId(userId).stream().map(convertService::convert).toList());
    }

    @GetMapping
    ServerResponse<UserPostResponseDto> getUserPostsById(@RequestParam(value = "id") Integer id) {
        return new ServerResponse<>(convertService.convert(integrationService.findPostById(id)));
    }

    @PatchMapping
    ServerResponse<UserPostResponseDto> updateUserPost(@RequestParam(value = "id") Integer id, @RequestBody UpdateUserPostRequestDto updateUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.updatePost(convertService.convert(updateUserPostRequestDto, id))));
    }
}
