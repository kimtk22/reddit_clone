package com.ktk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query("SELECT C FROM Comment C "
			+ "WHERE C.post=:post AND C.parent is null "
			+ "ORDER BY C.createdDate DESC")
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByMember(Member member);
}
