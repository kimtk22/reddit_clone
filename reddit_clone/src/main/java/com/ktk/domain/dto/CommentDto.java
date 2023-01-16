package com.ktk.domain.dto;

import java.time.Instant;

import com.ktk.domain.entity.CommentLayer;

import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	private Long postId;
	private String text;
	private String duration;
	private String username;
	private Long groupId;
	private CommentLayer layer;
}
