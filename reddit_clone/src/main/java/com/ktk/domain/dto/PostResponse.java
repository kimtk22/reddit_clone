package com.ktk.domain.dto;

import lombok.Data;

@Data
public class PostResponse {
	 private Long id;
	 private String postName;
	 private String url;
	 private String description;
	 private String userId;
	 private String userName;
	 private String subredditName;
	 private Integer voteCount;
	 private Integer commentCount;
	 private String duration;
}
