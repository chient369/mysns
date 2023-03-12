package com.mySns.model;

import java.util.List;

public class CommentSliceDTO {
	public final Integer ITEM_PER_SLICE=4; 
	private Integer nextSlice;
	private boolean hasNextSlice = true;
	private List<Comment> comments;

	public CommentSliceDTO() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public CommentSliceDTO(List<Comment> comments) {
		super();
		this.comments = comments;
	}
	
	public Integer getNextSlice() {
		return nextSlice;
	}

	public void setNextSlice(Integer nextSlice) {
		this.nextSlice = nextSlice;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isHasNextSlice() {
		return hasNextSlice;
	}

	public void setHasNextSlice(boolean hasNextSlice) {
		this.hasNextSlice = hasNextSlice;
	}
	

}
