package com.ktk.mapper;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.ocpsoft.prettytime.PrettyTime;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
	
	@Mappings({
		@Mapping(target = "postId", source = "post.postId"),
		@Mapping(target = "username", source = "member.name"),
		@Mapping(target = "duration", expression = "java(getDuration(comment))")
	})
	public abstract CommentDto toDto(Comment comment);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "text", source = "commentDto.text"),
		@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
		@Mapping(target = "post", source = "post"),
		@Mapping(target = "member", source = "member"),
	})
	public abstract Comment toEntity(CommentDto commentDto, Post post, Member member);
	
	String getDuration(Comment comment) {
    	PrettyTime prettyTime = new PrettyTime();
    	return prettyTime.format(Date.from(comment.getCreatedDate()));
    }
}
