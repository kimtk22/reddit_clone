package com.ktk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.domain.entity.Vote;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndMemberOrderByVoteIdDesc(Post post, Member currentMember);
}
