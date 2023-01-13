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
	private final JwtAuthService authService; 
	
	public List<SubredditDto> getAll(){
		return subredditRepository.findAll()
				.stream()
//				.map(this::mapToDto)
				.map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional
    public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditMapper.mapDtoToSubReddit(subredditDto, authService.getCurrentMember());
        Subreddit savedSubreddit = subredditRepository.save(subreddit);
        subredditDto.setId(savedSubreddit.getId());
        return subredditDto;
    }
	
	@Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("Subreddit not found with id -" + id));
//        return mapToDto(subreddit);
        return subredditMapper.mapSubredditToDto(subreddit); 
    }
	
}
