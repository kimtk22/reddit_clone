package com.ktk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktk.domain.dto.CommentDto;
import com.ktk.domain.dto.PostRequest;
import com.ktk.domain.dto.PostResponse;
import com.ktk.domain.dto.RegisterRequest;
import com.ktk.domain.dto.SubredditDto;
import com.ktk.service.CommentService;
import com.ktk.service.JwtAuthService;
import com.ktk.service.PostService;
import com.ktk.service.SubredditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
	
	private final PostService  postService;
	private final JwtAuthService authService;
	private final CommentService commentService;
	private final SubredditService subredditService;
	
	@GetMapping("/")
	public ModelAndView main(@AuthenticationPrincipal User user, ModelAndView mav) {
		
		mav.setViewName("index");
		
		List<PostResponse> posts = postService.getAllPost();
		List<SubredditDto> subreddits = subredditService.getAll();
		
		mav.addObject("posts", posts);
		mav.addObject("subreddits", subreddits);
		mav.addObject("user", user);
		return mav;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup(@AuthenticationPrincipal User user, ModelAndView mav) {
		String viewName = "";
		
		if(user == null) {
			viewName = "signup";
		}else {
			viewName = "redirect:/";
		}
		
		mav.setViewName(viewName);
		
		return mav;
	}
	
	@PostMapping("/signup")
	public ModelAndView signup(@ModelAttribute RegisterRequest request, ModelAndView mav) {
		authService.singup(request);
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView login(@AuthenticationPrincipal User user, @RequestParam(required = false) String error, ModelAndView mav) {
		String viewName = "";
		
		if(user == null) {
			viewName = "login";
			mav.addObject("error", error);
		}else {
			viewName = "redirect:/";
		}
		
		mav.setViewName(viewName);
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
	
	@GetMapping("/detailPost/{postId}")
	public ModelAndView detailPost(@PathVariable Long postId, ModelAndView mav) {
		PostResponse post = postService.getPostById(postId);
		List<CommentDto> comments = commentService.getAllByPostId(postId);
		
		mav.addObject("post", post);
		mav.addObject("comments", comments);
		
		mav.setViewName("detailPost");
		return mav;
	}
	
	@GetMapping("/userProfile")
	public ModelAndView userProfile(ModelAndView mav) {
		mav.setViewName("userProfile");
		return mav;
	}
}

