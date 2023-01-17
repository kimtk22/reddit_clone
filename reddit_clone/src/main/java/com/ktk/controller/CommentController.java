package com.ktk.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.entity.Comment;
import com.ktk.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@GetMapping("/by-postId/{postId}")
	public ResponseEntity<List<CommentDto>> getAllByPostId(@PathVariable Long postId){
		List<CommentDto> comments = commentService.getAllByPostId(postId);
		return ResponseEntity.ok().body(comments);
	}
	
	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentDto>> getAllByUserName(@PathVariable String userName){
		List<CommentDto> comments = commentService.getAllByUserName(userName);
		return ResponseEntity.ok().body(comments);
	}
	
	@PostMapping()
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
		commentService.createComment(commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
}
