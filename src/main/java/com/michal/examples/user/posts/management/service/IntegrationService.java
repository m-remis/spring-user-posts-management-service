package com.michal.examples.user.posts.management.service;

import com.michal.examples.user.posts.management.vo.UserPostVo;

import java.util.List;

/**
 * @author Michal Remis
 */
public interface IntegrationService {
    UserPostVo findPostById(Integer postId);

    List<UserPostVo> findPostByUserId(Integer userId);

    UserPostVo createPost(UserPostVo userPostVo);

    UserPostVo updatePost(UserPostVo userPostVo);

    void deletePost(Integer id);
}
