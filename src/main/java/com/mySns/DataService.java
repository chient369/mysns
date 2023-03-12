package com.mySns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mySns.model.Comment;
import com.mySns.model.CommentSliceDTO;
import com.mySns.model.Posts;
import com.mySns.model.User;

@Service
public class DataService {

	@Autowired
	private Dao dao;

	public void saveUser(User user) {
		dao.saveUser(user);
	}

	public User findUserByUserNameAndPassword(String userName, String password) {
		return dao.findUserByUserNameAndPassword(userName, password);
	}

	public User findUserById(Integer id) {
		return dao.findUserById(id);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	/* post of data access method */

	public void savePost(Posts post) {
		dao.savePost(post);
	}

	public List<Posts> findAllPosts(Integer userId) {
		List<Posts> posts = dao.findAllPosts();
		Set<Integer> likedPostId = dao.findLikedPostByUserId(userId);

		for (Posts p : posts) {
			if (likedPostId.contains(p.getPostId())) {
				p.setLiked(true);
			}
		}

		return posts;

	}

	public List<Posts> findAllPostsByUserId(Integer userId) {
		List<Posts> posts = findAllPosts(userId);
		List<Posts> resultPosts = new ArrayList<>();
		Iterator<Posts> it = posts.iterator();
		while (it.hasNext()) {
			Posts p = it.next();
			if (p.getUser().getId() == userId) {
				resultPosts.add(p);
			}

		}
		return resultPosts;

	}

	public Posts findPostById(Integer postId) {
		return dao.findPostById(postId);
	}

	@Transactional
	public Posts updatePostOfLikeCount(Integer inDe, Integer postId, Integer userId) {
		return dao.updatePostOfLikeCount(inDe, postId, userId);

	}

	public Integer countLikeOfAllPosts(Integer userId) {
		return dao.countLikeOfAllPosts(userId);
	}

	public void updateUser(User user) {
		dao.updateUser(user);
	}

	public Comment saveComment(Integer postId, Integer userId, String content) {
		return dao.saveComment(postId, userId, content);
	}

	public CommentSliceDTO findCommentByPostId(Integer postId, Integer sliceNum) {
		CommentSliceDTO dto = new CommentSliceDTO();
		if (sliceNum == null || sliceNum == 0)
			sliceNum = 1;
		Integer offset = (sliceNum -1) * dto.ITEM_PER_SLICE;
		List<Comment> cmts  = dao.findAllCommentByPostIdPage(postId, dto.ITEM_PER_SLICE, offset);
		dto.setComments(cmts);
		if(cmts.size() < dto.ITEM_PER_SLICE) {
			dto.setHasNextSlice(false);
		}else {
			dto.setNextSlice(sliceNum + 1);
		}
		return dto;
	}

}
