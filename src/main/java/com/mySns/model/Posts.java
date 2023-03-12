package com.mySns.model;

import java.util.List;

public class Posts {
	private Integer postId;
	private User user;
	private String content;
	private String postedDate;
	private Integer likedCount;
	private boolean isLiked;
	private List<Comment> comments;

	public Posts() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Posts(User user, String content, String postedDate) {
		super();
		this.user = user;
		this.content = content;
		this.postedDate = postedDate;
	}

	

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	

	
}
