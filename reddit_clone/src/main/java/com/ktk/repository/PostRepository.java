package com.ktk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.domain.entity.Subreddit;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findALLByOrderByCreatedDateDesc();
    List<Post> findByMemberOrderByCreatedDateDesc(Member member);
}
