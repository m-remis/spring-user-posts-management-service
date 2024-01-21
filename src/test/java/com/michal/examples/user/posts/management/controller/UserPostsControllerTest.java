package com.michal.examples.user.posts.management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.examples.user.posts.management.TestDataFactory;
import com.michal.examples.user.posts.management.TestFixtures;
import com.michal.examples.user.posts.management.dto.response.ServerResponse;
import com.michal.examples.user.posts.management.dto.response.UserPostResponseDto;
import com.michal.examples.user.posts.management.repository.UserPostsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(value = "test")
class UserPostsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserPostsRepository userPostsRepository;

    @AfterEach
    void cleanUp() {
        userPostsRepository.deleteAll();
    }

    @Test
    @DisplayName("Successfully create post, user exists")
    void createUserPost_userExists() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDto();
        final var existingUserId = 1;

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .queryParam("userId", String.valueOf(existingUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(request.body(), response.getPayload().body());
        Assertions.assertEquals(request.title(), response.getPayload().title());
    }

    @Test
    @DisplayName("Unsuccessfully create post, user doesn't exists")
    void createUserPost_userDoesntExist() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDto();
        final var madeUpUser = 999;

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .queryParam("userId", String.valueOf(madeUpUser))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Unsuccessfully create post, null post title")
    void createUserPost_nullTitleInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoNullTitle();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Unsuccessfully create post, empty post title")
    void createUserPost_emptyTitleInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoEmptyTitle();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Unsuccessfully create post, null post body")
    void createUserPost_nullBodyInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoNullBody();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Unsuccessfully create post, empty post body")
    void createUserPost_emptyBodyInformation() throws Exception {

        final var request = TestDataFactory.buildCreateUserPostRequestDtoEmptyBody();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Unsuccessfully update post, empty post title")
    void updateUserPost_emptyTitleInformation() throws Exception {

        final var request = TestDataFactory.buildUpdateUserPostRequestDtoEmptyTitle();
        final var irrelevantId = 1;

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .queryParam("id", String.valueOf(irrelevantId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Unsuccessfully update post, empty post body")
    void updateUserPost_emptyBodyInformation() throws Exception {

        final var request = TestDataFactory.buildUpdateUserPostRequestDtoEmptyBody();
        final var irrelevantId = 1;

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .queryParam("id", String.valueOf(irrelevantId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("Successfully find post by id, post is in internal system")
    void findUserPost_PostShouldBeInInternalSystem() throws Exception {

        final var givenPostId = 17;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, 1);
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(createdEntity.getBody(), response.getPayload().body());
        Assertions.assertEquals(createdEntity.getTitle(), response.getPayload().title());
        Assertions.assertEquals(createdEntity.getId(), response.getPayload().id());
    }

    @Test
    @DisplayName("Successfully update post")
    void updateUserPost() throws Exception {

        final var givenPostId = 17;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, 1);
        final var request = TestDataFactory.buildUpdateUserPostRequestDto();
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        final var shouldBeUpdated = userPostsRepository.findById(givenPostId);

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertTrue(shouldBeUpdated.isPresent());
        Assertions.assertEquals(shouldBeUpdated.get().getBody(), request.body());
        Assertions.assertEquals(shouldBeUpdated.get().getTitle(), request.title());
    }

    @Test
    @DisplayName("Successfully find post in external system, save in internal")
    void findUserPost_ShouldFindPostInExternalSystemAndSaveInInternal() throws Exception {

        final var givenPostId = 1;

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(UserPostsController.CONTROLLER_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<UserPostResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        final var shouldBeSaved = userPostsRepository.findById(givenPostId);

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertTrue(shouldBeSaved.isPresent());
        Assertions.assertEquals(shouldBeSaved.get().getBody(), response.getPayload().body());
        Assertions.assertEquals(shouldBeSaved.get().getId(), response.getPayload().id());
        Assertions.assertEquals(shouldBeSaved.get().getTitle(), response.getPayload().title());
    }

    @Test
    @DisplayName("Successfully find post in internal system")
    void findUserPostByUserId_ShouldBeInInternalSystem() throws Exception {

        final var givenPostId = 17;
        final var givenUserId = 22;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, givenUserId);
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(UserPostsController.GET_FOR_USER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("userId", String.valueOf(givenUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<List<UserPostResponseDto>> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        Assertions.assertNotNull(response.getPayload());
        Assertions.assertEquals(createdEntity.getBody(), response.getPayload().get(0).body());
        Assertions.assertEquals(createdEntity.getTitle(), response.getPayload().get(0).title());
        Assertions.assertEquals(createdEntity.getId(), response.getPayload().get(0).id());
    }

    @Test
    @DisplayName("Unsuccessfully find post, user not found")
    void findUserPostByUserId_NotFound() throws Exception {

        final var notPresentUserId = 999;

        String contentAsString = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(UserPostsController.GET_FOR_USER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("userId", String.valueOf(notPresentUserId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ServerResponse<List<UserPostResponseDto>> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        Assertions.assertTrue(response.getPayload().isEmpty());
    }

    @Test
    @DisplayName("Successfully delete post")
    void deleteUserPost() throws Exception {

        final var givenPostId = 17;
        final var createdEntity = TestDataFactory.buildUserPostEntity(givenPostId, 1);
        TestFixtures.createUserPostEntity(userPostsRepository, createdEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(UserPostsController.CONTROLLER_PREFIX)
                                .queryParam("id", String.valueOf(givenPostId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final var shouldBeDeleted = userPostsRepository.findById(givenPostId);

        Assertions.assertTrue(shouldBeDeleted.isEmpty());
    }

    @Test
    @DisplayName("Unsuccessfully delete post, non existent post")
    void deleteUserPost_ShouldNotFindPost() throws Exception {

        final var irrelevantId = 17;

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(UserPostsController.CONTROLLER_PREFIX)
                                .queryParam("id", String.valueOf(irrelevantId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
