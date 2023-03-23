package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.entity.UserPostEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Remis
 */
public interface UserPostsService {

    UserPostEntity save(UserPostEntity entity);

    Optional<UserPostEntity> findById(Integer id);

    List<UserPostEntity> findAllByUserId(Integer userId);

    UserPostEntity update(UserPostEntity entity);
}
