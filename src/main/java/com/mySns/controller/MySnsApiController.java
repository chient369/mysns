package com.mySns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mySns.DataService;
import com.mySns.model.Comment;
import com.mySns.model.CommentDTO;
import com.mySns.model.CommentSliceDTO;
import com.mySns.model.Posts;
import com.mySns.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class MySnsApiController {

	@Autowired
	private DataService service;

	@GetMapping("/post/like")
	public String likeCount(@RequestParam Integer inDe, @RequestParam Integer postId, HttpServletRequest rq) {
		User loginedUser = getLoginedUser(rq);
		if (loginedUser == null)
			return null;
		Posts post = service.updatePostOfLikeCount(inDe, postId, loginedUser.getId());
		return post.getLikedCount().toString();
	}

	@GetMapping("/cmt/{postId}/{sliceNum}")
	public CommentSliceDTO getMoreComment(@PathVariable("postId") Integer postId, @PathVariable("sliceNum") Integer slice,
			HttpServletRequest rq) {
		User loginedUser = getLoginedUser(rq);
		if (loginedUser == null)
			return null;
		
		CommentSliceDTO dto = service.findCommentByPostId(postId, slice);
		
		return dto;
	}
	@PostMapping("/cmt/save")
	public Comment saveComment(CommentDTO dto) {
		return service.saveComment(dto.getPostId(),dto.getUserId(), dto.getContent());
	}

	private User getLoginedUser(HttpServletRequest rq) {
		HttpSession ss = rq.getSession();
		User user = (User) ss.getAttribute("user");
		return user;
	}
}
