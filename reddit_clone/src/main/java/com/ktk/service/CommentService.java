package com.ktk.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.entity.Comment;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.exception.RedditException;
import com.ktk.mapper.CommentMapper;
import com.ktk.repository.CommentRepository;
import com.ktk.repository.MemberRepository;
import com.ktk.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	private final JwtAuthService authService;
	private final CommentMapper commentMapper;
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	
	public List<CommentDto> getAllByPostId(Long postId){
		Post post = postRepository.findById(postId).orElseThrow(() -> new RedditException("Not Found Post with postId - " + postId));
		return commentRepository.findAllByPost(post).stream()
								.map(commentMapper::toDto)
								.collect(Collectors.toList());
	}
	
	public List<CommentDto> getAllByUserName(String userName){
		Member member = memberRepository.findByName(userName).orElseThrow(() -> new RedditException("Not Found User with username - " + userName));
		return commentRepository.findAllByMember(member).stream()
													.map(commentMapper::toDto)
													.collect(Collectors.toList());
	}
	
	@Transactional
	public void createComment(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
									.orElseThrow(() -> new RedditException("Not Found Post with postId - " + commentDto.getPostId()));
		
		Comment comment = commentMapper.toEntity(commentDto, post, authService.getCurrentMember());
		comment = commentRepository.save(comment);
		
	}
	
	public CommentDto updateLikes(CommentDto commentDto) {
		 Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new RedditException("Not Found Comment with commentId - " + commentDto.getId()));
		 comment.setLikes(comment.getLikes() + commentDto.getLikes());
		 
		 commentRepository.save(comment);
		 
		 return commentMapper.toDto(comment);
	}
}
