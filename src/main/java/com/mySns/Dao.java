package com.mySns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mySns.model.Comment;
import com.mySns.model.Posts;
import com.mySns.model.User;

@Component
public class Dao {

	@Autowired
	private JdbcTemplate jdbc;

	
	//User data access
	public void saveUser(User user) {
		String sql = "INSERT INTO user(USER_NAME,PASSWORD,ENJOYED_DATE,IMAGE,PR) VALUES(?,?,?,?,?);";
		jdbc.update(sql, user.getUserName(), user.getPassword(), user.getEnjoyedDate(), user.getImage(), user.getPr());
	}

	public void updateUser(User user) {
		String sql = "UPDATE user SET USER_NAME = ? ,PASSWORD = ? ,ENJOYED_DATE =?,IMAGE=?,PR=? WHERE USER_ID = ?;";
		jdbc.update(sql, user.getUserName(), user.getPassword(), user.getEnjoyedDate(), user.getImage(), user.getPr(),
				user.getId());
	}

	public User findUserByUserNameAndPassword(String userName, String password) {
		String sql = "SELECT * FROM user WHERE user_name = ? and password = ?;";
		User user = null;
		try {
			user = jdbc.queryForObject(sql, new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User mapUser = new User();
					mapUser.setId(rs.getInt("user_id"));
					mapUser.setUserName(rs.getString("user_name"));
					mapUser.setEnjoyedDate(rs.getString("enjoyed_date"));
					mapUser.setPassword(rs.getString("password"));
					mapUser.setImage(rs.getString("image"));
					mapUser.setPr(rs.getString("pr"));
					return mapUser;
				}

			}, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not found User");
		}
		return user;
	}

	public User findUserById(Integer id) {
		String sql = "SELECT * FROM user WHERE user_id = ?;";
		User user = null;
		try {
			user = jdbc.queryForObject(sql, new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User mapUser = new User();
					mapUser.setId(rs.getInt("user_id"));
					mapUser.setUserName(rs.getString("user_name"));
					mapUser.setEnjoyedDate(rs.getString("enjoyed_date"));
					mapUser.setPassword(rs.getString("password"));
					mapUser.setImage(rs.getString("image"));
					mapUser.setPr(rs.getString("pr"));
					return mapUser;
				}

			}, id);
		} catch (Exception e) {
			System.out.println("Not found User");
		}
		return user;
	}

	public List<User> findAllUsers() {
		String sql = "SELECT * FROM user;";
		List<User> users = jdbc.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User mapUser = new User();
				mapUser.setId(rs.getInt("user_id"));
				mapUser.setUserName(rs.getString("user_name"));
				mapUser.setEnjoyedDate(rs.getString("enjoyed_date"));
				mapUser.setPassword(rs.getString("password"));
				mapUser.setImage(rs.getString("image"));
				mapUser.setPr(rs.getString("pr"));
				return mapUser;
			}

		});
		return users;
	}

	/* post of data access method */

	public void savePost(Posts post) {
		String sql = "INSERT INTO posts(USER_ID,CONTENT,POSTED_DATE) VALUES(?,?,?);";
		jdbc.update(sql, post.getUser().getId(), post.getContent(), post.getPostedDate());
	}

	public List<Posts> findAllPosts() {
		String sql = "SELECT * FROM posts ORDER BY POSTED_DATE DESC;";
		List<Posts> posts = jdbc.query(sql, new RowMapper<Posts>() {

			@Override
			public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
				Posts post = new Posts();
				post.setPostId(rs.getInt("post_id"));
				post.setContent(rs.getString("content"));
				post.setPostedDate(rs.getString("posted_date"));
				post.setLikedCount(rs.getInt("like_count"));
				post.setUser(findUserById(Integer.parseInt(rs.getString("user_id"))));
				post.setComments(findAllCommentByPostId(rs.getInt("post_id")));
				return post;
			}

		});
		return posts;

	}

	public List<Posts> findAllPostsByUserId(Integer userId) {
		String sql = "SELECT * FROM posts WHERE USER_ID = ? ORDER BY POSTED_DATE DESC;";
		List<Posts> posts = jdbc.query(sql, new RowMapper<Posts>() {

			@Override
			public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
				Posts post = new Posts();
				post.setPostId(rs.getInt("post_id"));
				post.setContent(rs.getString("content"));
				post.setPostedDate(rs.getString("posted_date"));
				post.setLikedCount(rs.getInt("like_count"));
				post.setUser(findUserById(Integer.parseInt(rs.getString("user_id"))));
				post.setComments(findAllCommentByPostId(rs.getInt("post_id")));
				return post;
			}

		}, userId);
		return posts;

	}

	public Posts findPostById(Integer postId) {
		String sql = "SELECT * FROM posts WHERE post_id = ?;";
		Posts post = jdbc.queryForObject(sql, new RowMapper<Posts>() {

			@Override
			public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
				Posts post = new Posts();
				post.setPostId(rs.getInt("post_id"));
				post.setContent(rs.getString("content"));
				post.setPostedDate(rs.getString("posted_date"));
				post.setLikedCount(rs.getInt("like_count"));
				post.setUser(findUserById(Integer.parseInt(rs.getString("user_id"))));
				post.setComments(findAllCommentByPostId(postId));
				return post;
			}

		}, postId);
		return post;
	}
	
	//Like post data access

	private final Integer INCREASE = 1;

	public Posts updatePostOfLikeCount(Integer inDe, Integer postId, Integer userId) {
		Posts post = findPostById(postId);
		String updateLikeCount = "UPDATE posts SET LIKE_COUNT = ? WHERE POST_ID =?";
		String addLikedPostWithUser = "INSERT INTO liked_post(POST_ID,USER_ID) VALUES(?,?)";
		String removeLikedPostWithUser = "DELETE FROM liked_post WHERE POST_ID = ? AND USER_ID = ?";
		if (inDe == INCREASE) {
			jdbc.update(updateLikeCount, post.getLikedCount() + 1, postId);
			jdbc.update(addLikedPostWithUser, postId, userId);
		} else {
			Integer count = (post.getLikedCount() - 1) <= 0 ? 0 : (post.getLikedCount() - 1);
			jdbc.update(updateLikeCount, count, postId);
			jdbc.update(removeLikedPostWithUser, postId, userId);
		}
		return findPostById(postId);

	}

	public Set<Integer> findLikedPostByUserId(Integer userId) {
		Set<Integer> likedPostIdSet = new HashSet<>();
		String sql = "SELECT post_id FROM liked_post WHERE user_id = ?;";
		List<Map<String, Object>> rs = (List<Map<String, Object>>) jdbc.queryForList(sql, userId);
		Iterator<Map<String, Object>> it = rs.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			likedPostIdSet.add((Integer) map.get("post_id"));
		}
		return likedPostIdSet;
	}

	public Integer countLikeOfAllPosts(Integer userId) {
		String sql = "SELECT SUM(like_count) as like_num FROM posts WHERE user_id = ?;";
		Integer rs = jdbc.queryForObject(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("like_num");
			}

		}, userId);
		System.out.println(rs);
		return rs;
	}

	// comment of data access
	public Comment saveComment(Integer postId, Integer userId, String content) {
		
		String sql = "INSERT INTO comment(POST_ID,USER_ID,CONTENT) VALUES(?,?,?)";
		int rs = jdbc.update(sql, postId, userId, content);
		if(rs > 0) {
			Comment savedCmt = new Comment();
			savedCmt.setPostId(postId);
			savedCmt.setUser(findUserById(userId));
			savedCmt.setContent(content);
			return savedCmt;
		}
		return null;
	}

	public List<Comment> findAllCommentByPostIdPage(Integer postId, Integer size, Integer page) {
		String sql = "SELECT * FROM comment WHERE POST_ID =? LIMIT ? OFFSET ?";
		List<Comment> comments = jdbc.query(sql, new RowMapper<Comment>() {

			@Override
			public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Comment cmt = new Comment();
				cmt.setId(rs.getInt("id"));
				cmt.setPostId(rs.getInt("post_id"));
				cmt.setContent(rs.getString("content"));
				cmt.setUser(findUserById(rs.getInt("user_id")));
				return cmt;
			}

		}, postId,size,page);
		return comments;
	}
	public List<Comment> findAllCommentByPostId(Integer postId) {
		String sql = "SELECT * FROM comment WHERE POST_ID =? LIMIT 4";
		List<Comment> comments = jdbc.query(sql, new RowMapper<Comment>() {

			@Override
			public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Comment cmt = new Comment();
				cmt.setId(rs.getInt("id"));
				cmt.setPostId(rs.getInt("post_id"));
				cmt.setContent(rs.getString("content"));
				cmt.setUser(findUserById(rs.getInt("user_id")));
				return cmt;
			}

		}, postId);
		return comments;
	}

}
