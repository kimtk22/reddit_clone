package com.ktk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByMember(Member member);
}
