package com.ktk.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.domain.dto.SubredditDto;
import com.ktk.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubRedditController {
	private final SubredditService subredditService;
	
	@GetMapping
	public List<SubredditDto> getAll(){
		return subredditService.getAll();
	}
	
	@GetMapping("/{id}")
	public SubredditDto getById(@PathVariable Long id){
		return subredditService.getSubreddit(id);
	}
	
	@PostMapping
	public SubredditDto save(@RequestBody SubredditDto subredditDto) {
		return subredditService.save(subredditDto);
	}
}
