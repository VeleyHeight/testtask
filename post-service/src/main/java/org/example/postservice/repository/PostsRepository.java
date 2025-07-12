package org.example.postservice.repository;

import org.example.postservice.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findAllByUserId(Integer integer);
}
