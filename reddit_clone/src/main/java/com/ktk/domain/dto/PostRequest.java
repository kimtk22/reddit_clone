package com.ktk.domain.dto;

import lombok.Data;

@Data
public class PostRequest {
	private String subredditName;
	private String postName;
	private String url;
	private String description;
}
