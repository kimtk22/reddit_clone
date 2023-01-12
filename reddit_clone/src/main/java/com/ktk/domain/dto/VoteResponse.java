package com.ktk.domain.dto;

import lombok.Data;

@Data
public class VoteResponse {
	private Long postId;
	private Integer voteCount;
}
