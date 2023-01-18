package com.ktk.domain.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	private String text;
	private String duration;
	private int likes;
	private Long postId;
	private String username;
	private Long parentId;
	private List<CommentDto> subComments;
}
