package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.vo.UserPostVo;
import com.amcef.user.posts.management.entity.UserPostEntity;
import org.springframework.stereotype.Component;

/**
 * @author Michal Remis
 */
@Component
public class ConvertServiceImpl implements ConvertService {

    @Override
    public UserPostVo convert(CreateUserPostRequestDto dto, Integer userId) {
        return new UserPostVo(
                null,
                userId,
                dto.title(),
                dto.body()
        );
    }

    @Override
    public UserPostVo convert(UpdateUserPostRequestDto dto, Integer postId) {
        return new UserPostVo(
                postId,
                null,
                dto.title(),
                dto.body()
        );
    }

    @Override
    public UserPostResponseDto convert(UserPostVo userPostVo) {
        return new UserPostResponseDto(
                userPostVo.id(),
                userPostVo.title(),
                userPostVo.body()
        );
    }

    @Override
    public UserPostVo convert(UserPostEntity entity) {
        return new UserPostVo(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getBody()
        );
    }

    @Override
    public UserPostVo convert(JsonPlaceHolderPostResponseDto jsonPlaceHolderPostResponseDto) {
        return new UserPostVo(
                jsonPlaceHolderPostResponseDto.id(),
                jsonPlaceHolderPostResponseDto.userId(),
                jsonPlaceHolderPostResponseDto.title(),
                jsonPlaceHolderPostResponseDto.body()
        );
    }
}
