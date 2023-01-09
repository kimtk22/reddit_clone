package com.ktk.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktk.domain.dto.PostRequest;
import com.ktk.domain.dto.PostResponse;
import com.ktk.domain.dto.SubredditDto;
import com.ktk.service.PostService;
import com.ktk.service.SubredditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
	
	private final PostService  postService;
	private final SubredditService subredditService;
	
	@GetMapping
	public ModelAndView main(ModelAndView mav) {
		mav.setViewName("index");
		
		List<PostResponse> posts = postService.getAllPost();
		List<SubredditDto> subreddits = subredditService.getAll();
		
		mav.addObject("posts", posts);
		mav.addObject("subreddits", subreddits);
		return mav;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup(ModelAndView mav) {
		mav.setViewName("signup");
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login");
		return mav;
	}
	
	@GetMapping("/createSubreddit")
	public ModelAndView createSubreddit(ModelAndView mav) {
		mav.setViewName("createSubreddit");
		return mav;
	}
	
	@GetMapping("/listSubreddit")
	public ModelAndView listSubreddit(ModelAndView mav) {
		mav.setViewName("listSubreddit");
		return mav;
	}
	
	@GetMapping("/createPost")
	public ModelAndView createPostPage(ModelAndView mav) {
		mav.setViewName("createPost");
		
		List<SubredditDto> subreddits = subredditService.getAll();
		
		mav.addObject("subreddits", subreddits);
		
		return mav;
	}
	
	@PostMapping("/createPost")
	public ModelAndView createPost(@ModelAttribute PostRequest postRequest, ModelAndView mav) {
		mav.setViewName("redirect:/");
		postService.save(postRequest);
		return mav;
	}
	
	@GetMapping("/detailPost")
	public ModelAndView detailPost(ModelAndView mav) {
		mav.setViewName("detailPost");
		return mav;
	}
	
	@GetMapping("/userProfile")
	public ModelAndView userProfile(ModelAndView mav) {
		mav.setViewName("userProfile");
		return mav;
	}
}

