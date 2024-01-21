package com.michal.examples.user.posts.management.client;

import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderUserResponseDto;
import com.michal.examples.user.posts.management.exception.ClientIntegrationException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author Michal Remis
 */
@Component
public class JsonPlaceHolderClientImpl implements JsonPlaceHolderClient {

    private static final String GET_POSTS = "/posts";
    private static final String GET_USERS = "/users";

    private static final String ID_PARAM = "id";

    private final RestClient restClient;

    public JsonPlaceHolderClientImpl(final RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<JsonPlaceHolderUserResponseDto> findUserById(String baseUrl, Integer userId) {
        try {
            return restClient.get()
                    .uri(UriComponentsBuilder.fromUriString(baseUrl.concat(GET_USERS)).queryParam(ID_PARAM, userId).build().toUri())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<JsonPlaceHolderUserResponseDto>>() {})
                    .getBody();
        } catch (HttpClientErrorException exception) {
            throw ClientIntegrationException.of("Could not retrieve user data");
        }
    }

    @Override
    public List<JsonPlaceHolderPostResponseDto> findPostById(String baseUrl, Integer postId) {
        try {
            return restClient.get()
                    .uri(UriComponentsBuilder.fromUriString(baseUrl.concat(GET_POSTS)).queryParam(ID_PARAM, postId).build().toUri())
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<JsonPlaceHolderPostResponseDto>>() {})
                    .getBody();
        } catch (HttpClientErrorException exception) {
            throw ClientIntegrationException.of("Could not retrieve user post data");
        }
    }
}
