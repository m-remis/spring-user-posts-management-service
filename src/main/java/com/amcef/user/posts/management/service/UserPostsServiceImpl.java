package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.entity.UserPostEntity;
import com.amcef.user.posts.management.exception.NotFoundException;
import com.amcef.user.posts.management.repository.UserPostsRepository;
import com.amcef.user.posts.management.vo.UserPostVo;
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
    public UserPostEntity save(UserPostVo userPostVo) {
        LOGGER.info("Saving post for user with id: [{}]", userPostVo.userId());
        return repository.save(
                new UserPostEntity(
                        Optional.ofNullable(userPostVo.id()).orElse(repository.getNextAvailableId()),
                        userPostVo.userId(),
                        userPostVo.title(),
                        userPostVo.body()
                )
        );
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
    public UserPostEntity update(UserPostVo userPostVo) {
        return repository.findById(userPostVo.id()).map(found -> {
            LOGGER.info("Updating post with id: [{}]", found.getId());
            Optional.ofNullable(userPostVo.body()).ifPresent(found::setBody);
            Optional.ofNullable(userPostVo.title()).ifPresent(found::setTitle);
            return found;
        }).orElseThrow(() -> {
            LOGGER.info("Could not find post with id: [{}]", userPostVo.id());
            throw NotFoundException.of("Could not find user post");
        });
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (findById(id).isEmpty()) {
            LOGGER.info("Could not find post with id: [{}]", id);
            throw NotFoundException.of(String.format("Could not find user post with id: [%s]", id));
        }
        LOGGER.info("Removing user post with id: [{}]", id);
        repository.deleteById(id);
    }
}


