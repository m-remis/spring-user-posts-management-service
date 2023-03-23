package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.entity.UserPostEntity;
import com.amcef.user.posts.management.exception.NotFoundException;
import com.amcef.user.posts.management.repository.UserPostsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Remis
 */
@Service
@Transactional(readOnly = true)
public class UserPostsServiceImpl implements UserPostsService {

    private final UserPostsRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPostsServiceImpl.class);

    public UserPostsServiceImpl(final UserPostsRepository repository) {
        this.repository = repository;
    }

    /**
     * That id generation was conscious choice
     * I don't like to rely on DB auto increment, this would ideally be String UUID, but I have to work with Integers
     */
    @Override
    @Transactional
    public UserPostEntity save(UserPostEntity entity) {
        LOGGER.info("Saving post for user with id: [{}]", entity.getUserId());
        entity.setId(repository.getNextAvailableId());
        return repository.save(entity);
    }

    @Override
    public Optional<UserPostEntity> findById(Integer id) {
        LOGGER.info("Finding post with id: [{}]", id);
        return repository.findById(id);
    }

    @Override
    public List<UserPostEntity> findAllByUserId(Integer userId) {
        LOGGER.info("Finding post for user with id: [{}]", userId);
        return repository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public UserPostEntity update(UserPostEntity entity) {
        return repository.findById(entity.getId()).map(found -> {
            LOGGER.info("Updating post with id: [{}]", entity.getId());
            Optional.ofNullable(entity.getBody()).ifPresent(found::setBody);
            Optional.ofNullable(entity.getTitle()).ifPresent(found::setTitle);
            return found;
        }).orElseThrow(() -> {
            LOGGER.info("Could not find post with id: [{}]", entity.getId());
            throw NotFoundException.of("Could not find user post");
        });
    }
}


