package com.ktk.mapper;

import java.time.Instant;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktk.domain.dto.PostRequest;
import com.ktk.domain.dto.PostResponse;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.domain.entity.Subreddit;
import com.ktk.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Mappings({
		@Mapping(target = "postId", ignore = true),
		@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
		@Mapping(target = "subreddit", source = "subreddit"),
		@Mapping(target = "member", source = "member"),
		@Mapping(target = "description", source = "postRequest.description"),
		@Mapping(target = "postName", source = "postRequest.postName"),
		@Mapping(target = "url", source = "postRequest.url"),
		@Mapping(target = "voteCount", constant = "0")
	})
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, Member member);
	
	@Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
	@Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
	@Mapping(target = "userName", source = "member.name")
    @Mapping(target = "subredditName", source = "subreddit.name")
//	@Mapping(target = "voteCount", source = "voteCount")
	@Mapping(target = "voteCount", expression = "java(voteCount(post))")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse mapToDto(Post post);
	
	Integer commentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }
	
	Integer voteCount(Post post) {
		return post.getVoteCount() == null ? 0 : post.getVoteCount();
	}

    String getDuration(Post post) {
    	PrettyTime prettyTime = new PrettyTime();
    	return prettyTime.format(Date.from(post.getCreatedDate()));
    }
}
