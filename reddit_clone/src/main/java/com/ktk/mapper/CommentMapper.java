package com.ktk.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.exception.RedditException;
import com.ktk.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Mappings({
		@Mapping(target = "id", source = "id"),
		@Mapping(target = "text", source = "text"),
		@Mapping(target = "duration", expression = "java(getDuration(comment))"),
		@Mapping(target = "likes", source = "likes"),
		@Mapping(target = "postId", source = "post.postId"),
		@Mapping(target = "username", source = "member.name"),
		@Mapping(target = "parentId", source = "parent.id"),
		@Mapping(target = "subComments", source = "subComments"),
	})
	public abstract CommentDto toDto(Comment comment);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "text", source = "commentDto.text"),
		@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
		@Mapping(target = "likes", ignore = true),
		@Mapping(target = "post", source = "post"),
		@Mapping(target = "member", source = "member"),
		@Mapping(target = "parent", expression = "java(getParentComment(commentDto.getParentId()))"),
		@Mapping(target = "subComments", ignore = true)
	})
	public abstract Comment toEntity(CommentDto commentDto, Post post, Member member);
	
	String getDuration(Comment comment) {
    	PrettyTime prettyTime = new PrettyTime();
    	return prettyTime.format(Date.from(comment.getCreatedDate()));
    }
	
	List<CommentDto> getSubCommentDto(List<Comment> comments){
		return comments.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	Comment getParentComment(Long parentId) {
		if(parentId == null) return null;
		return commentRepository.findById(parentId)
								.orElseThrow(() -> new RedditException("Not found parent comment by id - " + parentId));
	}
}
