package com.mySns.model;

public class User {

	private Integer id;
	private String userName;
	private String password;
	private String enjoyedDate;
	private String image;
	private String pr;

	public User() {

	}

	public User(String userName, String password, String pr) {
		super();
		this.userName = userName;
		this.password = password;
		this.pr = pr;
	}

	public User(String userName, String password, String enjoyedDate, String image) {
		super();
		this.userName = userName;
		this.password = password;
		this.enjoyedDate = enjoyedDate;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnjoyedDate() {
		return enjoyedDate;
	}

	public void setEnjoyedDate(String enjoyedDate) {
		this.enjoyedDate = enjoyedDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", enjoyedDate=" + enjoyedDate
				+ ", image=" + image + "]";
	}

}
