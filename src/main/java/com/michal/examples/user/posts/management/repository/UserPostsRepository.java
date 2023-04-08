package com.michal.examples.user.posts.management.repository;

import com.michal.examples.user.posts.management.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Michal Remis
 */
@Repository
public interface UserPostsRepository extends JpaRepository<UserPostEntity, Integer> {

    @Query("""
            select upe
            from UserPostEntity upe
            where upe.id = :id
            """)
    Optional<UserPostEntity> findById(@Param("id") Integer id);

    @Query("""
            select upe
            from UserPostEntity upe
            where upe.userId = :userId
            """)
    List<UserPostEntity> findAllByUserId(@Param("userId") Integer userId);

    @Query("""
            delete UserPostEntity upe
            where upe.id = :id
            """)
    @Modifying
    void deleteById(@Param("id") Integer id);

    @Query("""
            select coalesce(max(upe.id + 1), 1)
            from UserPostEntity upe
            """)
    Integer getNextAvailableId();
}
