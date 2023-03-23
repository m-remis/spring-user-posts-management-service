package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.client.JsonPlaceHolderClient;
import com.amcef.user.posts.management.dto.response.JsonPlaceHolderUserResponseDto;
import com.amcef.user.posts.management.exception.NotFoundException;
import com.amcef.user.posts.management.vo.UserPostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Michal Remis
 */
@Service
public class IntegrationService {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;
    private final UserPostsService userPostsService;

    private final ConvertService convertService;

    private final String clientBaseUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationService.class);

    public IntegrationService(final JsonPlaceHolderClient jsonPlaceHolderClient,
                              final UserPostsService userPostsService,
                              final ConvertService convertService,
                              @Value("${application.placeholder-client.base-url}") final String clientBaseUrl) {
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
        this.userPostsService = userPostsService;
        this.convertService = convertService;
        this.clientBaseUrl = clientBaseUrl;
    }

    public UserPostVo findPostById(Integer postId) {
        // try to look in db
        return userPostsService.findById(postId).map(convertService::convert).orElseGet(() -> {
            // try to look into external system
            LOGGER.info("Could not find post in internal system, searching in external...");
            return Optional.ofNullable(jsonPlaceHolderClient.findPostById(clientBaseUrl, postId))
                    .map(convertService::convert)
                    // if not found anywhere, throw error
                    .orElseThrow(() -> NotFoundException.of("Could not find user post"));
        });
    }

    public Optional<JsonPlaceHolderUserResponseDto> findUserById(Integer userId) {
        return Optional.ofNullable(jsonPlaceHolderClient.findUserById(clientBaseUrl, userId));
    }
}
