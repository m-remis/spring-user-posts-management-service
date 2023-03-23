package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.vo.UserPostVo;
import com.amcef.user.posts.management.entity.UserPostEntity;
import org.springframework.stereotype.Component;

/**
 * @author Michal Remis
 */
@Component
public class ConvertService {

    public UserPostEntity convert(CreateUserPostRequestDto dto, Integer userId) {
        return new UserPostEntity(
                userId,
                dto.title(),
                dto.body()
        );
    }

    public UserPostVo convert(UserPostEntity entity) {
        return new UserPostVo(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getBody()
        );
    }

    public UserPostVo convert(JsonPlaceHolderPostResponseDto jsonPlaceHolderPostResponseDto) {
        return new UserPostVo(
                jsonPlaceHolderPostResponseDto.id(),
                jsonPlaceHolderPostResponseDto.userId(),
                jsonPlaceHolderPostResponseDto.title(),
                jsonPlaceHolderPostResponseDto.body()
        );
    }

    public UserPostResponseDto convert(UserPostVo userPostVo) {
        return new UserPostResponseDto(userPostVo.id(), userPostVo.title(), userPostVo.body());
    }
}
