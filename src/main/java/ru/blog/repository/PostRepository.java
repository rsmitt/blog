package ru.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "posts-user-graph")
    Page<Post> findAllByUserId(Long id, Pageable pageable);
}
