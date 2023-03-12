package com.mySns.model;

public class CommentDTO {
	private Integer postId;
	private Integer userId;
	private String content;

	public CommentDTO() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public CommentDTO(Integer postId, Integer userId, String content) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.content = content;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
