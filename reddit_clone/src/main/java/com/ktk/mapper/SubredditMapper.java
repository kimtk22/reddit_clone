package com.ktk.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ktk.domain.dto.SubredditDto;
import com.ktk.domain.entity.Member;
import com.ktk.domain.entity.Post;
import com.ktk.domain.entity.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	@Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "member", source = "member")
	Subreddit mapDtoToSubReddit(SubredditDto dto, Member member);
	
	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}
	

}
