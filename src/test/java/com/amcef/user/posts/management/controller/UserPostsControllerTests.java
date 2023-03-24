package com.amcef.user.posts.management.controller;

import com.amcef.user.posts.management.TestDataFactory;
import com.amcef.user.posts.management.TestFixtures;
import com.amcef.user.posts.management.dto.response.ServerResponse;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.repository.UserPostsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * @author Michal Remis
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserPostsControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserPostsRepository userPostsRepository;

    @AfterEach
    void cleanUp() {
        userPostsRepository.deleteAll();
    }

    @Test
    void createUserPost_userExists() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDto();
        final var existingUserId = 1;

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .queryParam("userId", String.valueOf(existingUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<UserPostResponseDto>>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(request.body(), response.getPayload().body());
        Assertions.assertEquals(request.title(), response.getPayload().title());
    }

    @Test
    void createUserPost_userDoesntExist() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDto();
        final var madeUpUser = 999;

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .queryParam("userId", String.valueOf(madeUpUser)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createUserPost_nullTitleInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoNullTitle();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createUserPost_emptyTitleInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoEmptyTitle();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createUserPost_nullBodyInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoNullBody();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createUserPost_emptyBodyInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoEmptyBody();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateUserPost_emptyTitleInformation() throws Exception {

        final var request = TestDataFactory.buildUpdateUserPostRequestDtoEmptyTitle();
        final var irrelevantId = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .queryParam("id", String.valueOf(irrelevantId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateUserPost_emptyBodyInformation() throws Exception {

        final var request = TestDataFactory.buildUpdateUserPostRequestDtoEmptyBody();
        final var irrelevantId = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .queryParam("id", String.valueOf(irrelevantId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void findUserPost_PostShouldBeInInternalSystem() throws Exception {

        final var givenPostId = 17;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, 1);
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<UserPostResponseDto>>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(createdEntity.getBody(), response.getPayload().body());
        Assertions.assertEquals(createdEntity.getTitle(), response.getPayload().title());
        Assertions.assertEquals(createdEntity.getId(), response.getPayload().id());
    }

    @Test
    void updateUserPost() throws Exception {

        final var givenPostId = 17;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, 1);
        final var request = TestDataFactory.buildUpdateUserPostRequestDto();
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .patch(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<UserPostResponseDto>>() {
        });

        final var shouldBeUpdated = userPostsRepository.findById(givenPostId);

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertTrue(shouldBeUpdated.isPresent());
        Assertions.assertEquals(shouldBeUpdated.get().getBody(), request.body());
        Assertions.assertEquals(shouldBeUpdated.get().getTitle(), request.title());
    }

    @Test
    void findUserPost_ShouldFindPostInExternalSystemAndSaveInInternal() throws Exception {

        final var givenPostId = 1;

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get(UserPostsController.CONTROLLER_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<UserPostResponseDto>>() {
        });

        final var shouldBeSaved = userPostsRepository.findById(givenPostId);

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertTrue(shouldBeSaved.isPresent());
        Assertions.assertEquals(shouldBeSaved.get().getBody(), response.getPayload().body());
        Assertions.assertEquals(shouldBeSaved.get().getId(), response.getPayload().id());
        Assertions.assertEquals(shouldBeSaved.get().getTitle(), response.getPayload().title());
    }

    @Test
    void findUserPostByUserId_ShouldBeInInternalSystem() throws Exception {

        final var givenPostId = 17;
        final var givenUserId = 22;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, givenUserId);
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get(UserPostsController.GET_FOR_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("userId", String.valueOf(givenUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<List<UserPostResponseDto>> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<List<UserPostResponseDto>>>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(createdEntity.getBody(), response.getPayload().get(0).body());
        Assertions.assertEquals(createdEntity.getTitle(), response.getPayload().get(0).title());
        Assertions.assertEquals(createdEntity.getId(), response.getPayload().get(0).id());
    }

    @Test
    void findUserPostByUserId_NotFound() throws Exception {

        final var notPresentUserId = 999;

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get(UserPostsController.GET_FOR_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("userId", String.valueOf(notPresentUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ServerResponse<List<UserPostResponseDto>> response = objectMapper.readValue(contentAsString, new TypeReference<ServerResponse<List<UserPostResponseDto>>>() {
        });

        Assertions.assertTrue(response.getPayload().isEmpty());
    }

}
