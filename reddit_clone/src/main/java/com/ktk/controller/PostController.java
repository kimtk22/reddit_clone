package com.ktk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.domain.dto.PostRequest;
import com.ktk.domain.dto.PostResponse;
import com.ktk.domain.dto.VoteDto;
import com.ktk.domain.dto.VoteResponse;
import com.ktk.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@GetMapping("/test")
	public String test(){
		return "It is test!!";
	}
	
	@GetMapping
	public List<PostResponse> getAllPosts(){
		return postService.getAllPost();
	}
	
	@GetMapping("/{id}")
	public PostResponse getPost(@PathVariable Long id){
		return postService.getPostById(id);
	}
	
	@GetMapping("/by-user/{name}")
	public List<PostResponse> getPostByUsername(@PathVariable String name){
		return postService.getPostsByUsername(name);
	}
	
	@GetMapping("/by-subreddit/{id}")
	public List<PostResponse> getPostBySubReddit(@PathVariable Long id){
		return postService.getPostsBySubreddit(id);
	}
	
	@PostMapping()
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
	@PostMapping("/vote")
    public ResponseEntity<VoteResponse> vote(@RequestBody VoteDto voteDto) {
        VoteResponse voteResponse = postService.vote(voteDto);
        return ResponseEntity.ok(voteResponse);
    }
	
}
