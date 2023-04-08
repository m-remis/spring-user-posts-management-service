package com.michal.examples.user.posts.management;

import com.michal.examples.user.posts.management.entity.UserPostEntity;
import com.michal.examples.user.posts.management.repository.UserPostsRepository;

/**
 * @author Michal Remis
 */
public class TestFixtures {

    public static UserPostEntity createUserPostEntity(UserPostsRepository repository, UserPostEntity userPostEntity) {
        return repository.save(userPostEntity);
    }
}
