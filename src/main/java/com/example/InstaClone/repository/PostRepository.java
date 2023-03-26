package com.example.InstaClone.repository;

import com.example.InstaClone.entity.Post;
import com.example.InstaClone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByCreateDesc(User user);

    List<Post> findAllByCreatedDateDesc();

    Optional<Post> findPostByIdAndUser(Long id, User user);
}
