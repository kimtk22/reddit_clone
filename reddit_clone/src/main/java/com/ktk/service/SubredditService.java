package com.ktk.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktk.domain.dto.SubredditDto;
import com.ktk.domain.entity.Subreddit;
import com.ktk.exception.RedditException;
import com.ktk.mapper.SubredditMapper;
import com.ktk.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
//	private final AuthService authService; 
	
	public List<SubredditDto> getAll(){
		return subredditRepository.findAll()
				.stream()
//				.map(this::mapToDto)
				.map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional
    public SubredditDto save(SubredditDto subredditDto) {
//        Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDto));
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubReddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }
	
	@Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("Subreddit not found with id -" + id));
//        return mapToDto(subreddit);
        return subredditMapper.mapSubredditToDto(subreddit); 
    }
	
	
//	private SubredditDto mapToDto(Subreddit subreddit) {
//		return SubredditDto.builder()
//							.id(subreddit.getId())
//							.name(subreddit.getName())
//							.description(subreddit.getDescription())
//							.postCount(subreddit.getPosts().size())
//							.build();
//	}
//	
//	private Subreddit mapToSubreddit(SubredditDto dto) {
//		return Subreddit.builder()
//						.name(dto.getName())
//						.description(dto.getDescription())
//						.createdDate(Instant.now())
//						.member(authService.getCurrentMember())
//						.build();
//	}
}
