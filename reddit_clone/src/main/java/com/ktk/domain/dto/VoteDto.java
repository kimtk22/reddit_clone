package com.ktk.domain.dto;

import com.ktk.domain.entity.VoteType;

import lombok.Data;

@Data
public class VoteDto {
	private VoteType voteType;
	private Long postId;
}
