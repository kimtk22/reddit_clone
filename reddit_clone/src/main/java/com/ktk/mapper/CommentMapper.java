package com.ktk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mappings({
		@Mapping(target = "postId", source = "post.postId"),
		@Mapping(target = "username", source = "member.name")
	})
	CommentDto mapToDto(Comment comment);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "text", source = "commentDto.text"),
		@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
		@Mapping(target = "post", source = "post"),
		@Mapping(target = "member", source = "member")
	})
	Comment map(CommentDto commentDto, Post post, Member member);
}
