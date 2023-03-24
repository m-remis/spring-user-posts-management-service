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
    ServerResponse<UserPostResponseDto> createUserPost(@RequestParam(value = "userId") Integer userId, @RequestBody @Validated CreateUserPostRequestDto createUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.createPost(convertService.convert(createUserPostRequestDto, userId))));
    }

    @GetMapping(GET_FOR_USER)
    ServerResponse<List<UserPostResponseDto>> getUserPostsByUserId(@RequestParam(value = "userId") Integer userId) {
        return new ServerResponse<>(integrationService.findPostByUserId(userId).stream().map(convertService::convert).toList());
    }

    @GetMapping(CONTROLLER_PREFIX)
    ServerResponse<UserPostResponseDto> getUserPostsById(@RequestParam(value = "id") Integer id) {
        return new ServerResponse<>(convertService.convert(integrationService.findPostById(id)));
    }

    @PatchMapping(CONTROLLER_PREFIX)
    ServerResponse<UserPostResponseDto> updateUserPost(@RequestParam(value = "id") Integer id, @RequestBody @Validated UpdateUserPostRequestDto updateUserPostRequestDto) {
        return new ServerResponse<>(convertService.convert(integrationService.updatePost(convertService.convert(updateUserPostRequestDto, id))));
    }
}
