package com.mySns.model;

public class Comment {

	private Integer id;
	private Integer postId;
	private User user;
	private String content;

	public Comment() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Comment(Integer id, Integer postId, User user, String content) {
		super();
		this.id = id;
		this.postId = postId;
		this.user = user;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
