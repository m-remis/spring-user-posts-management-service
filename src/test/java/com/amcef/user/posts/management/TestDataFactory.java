package com.amcef.user.posts.management;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.amcef.user.posts.management.entity.UserPostEntity;
import com.amcef.user.posts.management.vo.UserPostVo;

import java.util.UUID;

/**
 * @author Michal Remis
 */
public class TestDataFactory {

    public static CreateUserPostRequestDto buildCreateUserPostRequestDto() {
        return new CreateUserPostRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static CreateUserPostRequestDto buildCreateUserPostRequestDtoEmptyTitle() {
        return new CreateUserPostRequestDto("", UUID.randomUUID().toString());
    }

    public static CreateUserPostRequestDto buildCreateUserPostRequestDtoNullTitle() {
        return new CreateUserPostRequestDto(null, UUID.randomUUID().toString());
    }

    public static CreateUserPostRequestDto buildCreateUserPostRequestDtoNullBody() {
        return new CreateUserPostRequestDto(UUID.randomUUID().toString(), null);
    }

    public static CreateUserPostRequestDto buildCreateUserPostRequestDtoEmptyBody() {
        return new CreateUserPostRequestDto(UUID.randomUUID().toString(), "");
    }

    public static UpdateUserPostRequestDto buildUpdateUserPostRequestDtoEmptyBody() {
        return new UpdateUserPostRequestDto(UUID.randomUUID().toString(), "");
    }


    public static UpdateUserPostRequestDto buildUpdateUserPostRequestDtoEmptyTitle() {
        return new UpdateUserPostRequestDto("", UUID.randomUUID().toString());
    }

    public static UpdateUserPostRequestDto buildUpdateUserPostRequestDto() {
        return new UpdateUserPostRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static UserPostEntity buildUserPostEntity(Integer id, Integer userId) {
        return new UserPostEntity(id, userId, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
