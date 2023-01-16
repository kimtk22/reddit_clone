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
import com.ktk.domain.entity.Member;
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
	public ModelAndView main(ModelAndView mav) {
		
		mav.setViewName("index");
		
		List<PostResponse> posts = postService.getAllPost();
		List<SubredditDto> subreddits = subredditService.getAll();
		
		mav.addObject("posts", posts);
		mav.addObject("subreddits", subreddits);
		mav.addObject("user", authService.getCurrentMember());
		return mav;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup(ModelAndView mav) {
		String viewName = "";
		
		if(authService.getCurrentMember() == null) {
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
	public ModelAndView login(@RequestParam(required = false) String error, ModelAndView mav) {
		String viewName = "";
		
		if(authService.getCurrentMember() == null) {
			viewName = "login";
			mav.addObject("error", error);
		}else {
			viewName = "redirect:/";
		}
		
		mav.setViewName(viewName);
		return mav;
	}
	
	@GetMapping("/createSubreddit")
	public ModelAndView createSubredditPage(ModelAndView mav) {
		mav.setViewName("createSubreddit");
		return mav;
	}
	
	@PostMapping("/createSubreddit")
	public ModelAndView createSubreddit(@ModelAttribute SubredditDto subredditDto, ModelAndView mav) {
		subredditService.save(subredditDto);
		mav.setViewName("redirect:/");
		return mav;
	}
	
	@GetMapping("/listSubreddit")
	public ModelAndView listSubreddit(ModelAndView mav) {
		List<SubredditDto> subreddits = subredditService.getAll();
		mav.addObject("subreddits", subreddits);
		
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
		mav.addObject("post", postService.getPostById(postId));
		mav.addObject("comments", commentService.getAllByPostId(postId));
		mav.addObject("subreddits", subredditService.getAll());
		mav.addObject("user", authService.getCurrentMember());
		
		mav.setViewName("detailPost");
		return mav;
	}
	
	@PostMapping("/comment")
	public ModelAndView createComment(@ModelAttribute CommentDto commentDto, ModelAndView mav) {
		commentService.createComment(commentDto);
		
		mav.setViewName("redirect:/detailPost/" + commentDto.getPostId());
		return mav;
	}
	
	@GetMapping("/userProfile/{userId}")
	public ModelAndView userProfile(@PathVariable Long userId, ModelAndView mav) {
		
		Member member = authService.getMemberById(userId); 
		
		List<PostResponse> posts = postService.getPostsByUsername(member.getName());
		List<CommentDto> comments = commentService.getAllByUserName(member.getName());
		
		mav.addObject("user", authService.getCurrentMember());
		mav.addObject("member", member);
		mav.addObject("posts", posts);
		mav.addObject("comments", comments);
		
		mav.setViewName("userProfile");
		return mav;
	}
}

