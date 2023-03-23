package com.amcef.user.posts.management.controller;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.ServerResponse;
import com.amcef.user.posts.management.service.ConvertService;
import com.amcef.user.posts.management.service.IntegrationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    ServerResponse<Void> createUserPost(@RequestParam(value = "userId") Integer userId, @RequestBody CreateUserPostRequestDto createUserPostRequestDto) {
        return null;
    }
}
