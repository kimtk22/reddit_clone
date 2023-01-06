package com.ktk.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ktk.domain.dto.SubredditDto;
import com.ktk.domain.entity.Post;
import com.ktk.domain.entity.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	@Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubReddit(SubredditDto dto);
	
	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}
	

}
