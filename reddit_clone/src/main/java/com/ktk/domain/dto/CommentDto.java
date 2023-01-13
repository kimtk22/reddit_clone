package com.ktk.domain.dto;

import java.time.Instant;

import com.ktk.domain.entity.CommentLayer;

import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	private Long postId;
	private String text;
	private Instant createdDate;
	private String username;
	private Long groupId;
	private CommentLayer layer;
}
