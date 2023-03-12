package com.mySns.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mySns.DataService;
import com.mySns.model.Posts;
import com.mySns.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MySnsController {

	@Autowired
	private DataService service;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String loginPost(String username, String password, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = service.findUserByUserNameAndPassword(username, password);
		if (user == null) {
			model.addAttribute("msg", "パスワード又はユーザー名が間違います。やり直してください");
			return "login";
		}
		session.setAttribute("user", user);
		return "redirect:/home";
	}

	@RequestMapping(path = "/regist", method = RequestMethod.GET)
	public String viewRegist() {
		return "regist";
	}

	@RequestMapping(path = "/regist", method = RequestMethod.POST)
	public String viewRegistPost(String username, String password, Model model) {
		String enjoyedDate = getDateNow();
		User user = new User(username, password, enjoyedDate, getImageURL());
		try {
			service.saveUser(user);
		} catch (Exception e) {
			model.addAttribute("msg", "エラーがありました。やり直してください");
			return "regist";
		}
		model.addAttribute("msg", "登録完了！");
		return "login";
	}

	@RequestMapping(path = "/edit", method = RequestMethod.POST)
	public String editPost(String userName, String password, String pr, Integer id, HttpServletRequest request) {
		User dBUser = service.findUserById(id);
		if (userName != null)
			dBUser.setUserName(userName);
		if (password != null)
			dBUser.setPassword(password);
		dBUser.setPr(pr);
		service.updateUser(dBUser);

		request.getSession().setAttribute("user", dBUser);

		return "redirect:/profile";
	}

	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String viewHome(Model model, HttpServletRequest rq) {
		User loginedUser = getLoginedUser(rq);
		model.addAttribute("user", loginedUser);
		model.addAttribute("posts", service.findAllPosts(loginedUser.getId()));
		model.addAttribute("users", service.findAllUsers());
		System.out.println("Home Access of ss ID :" + rq.getRequestedSessionId());
		return "home";
	}

	/********* 以下に各機能用のメソッドを追加していこう **********/

	@GetMapping("/profile")
	public String viewProfile(Model model, HttpServletRequest rq) {
		User user = getLoginedUser(rq);
		model.addAttribute("user", user);
		model.addAttribute("users", service.findAllUsers());
		model.addAttribute("likedSum", service.countLikeOfAllPosts(user.getId()));
		model.addAttribute("posts", service.findAllPostsByUserId(user.getId()));

		return "profile";
	}

	@PostMapping("/posts")
	public String newPosts(String content, HttpServletRequest rq) {
		User user = getLoginedUser(rq);
		service.savePost(new Posts(user, content, getPostTime()));
		return "redirect:/home";
	}
	/*** {comment method} ***/
	
	
	/*** {utility method} ***/

	private String getPostTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM月dd日 hh:mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	private String getDateNow() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/mm/dd");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	private User getLoginedUser(HttpServletRequest rq) {
		HttpSession ss = rq.getSession();
		User user = (User) ss.getAttribute("user");
		return user;
	}

	private String getImageURL() {
		int rand = new Random().nextInt(9) + 1;
		String url = "https://bootdey.com/img/Content/avatar/avatar" + rand + ".png";
		return url;
	}
	
	
	/**
	 * vue.js用
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(path = "/vue/{vueData}", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
	public Map<String,String> vuepractice(@PathVariable String vueData) {
		Map<String,String> responseData = new HashMap<String,String>();
		Random rd = new Random();
		int rndNum = rd.nextInt(10) +1;
		String returnText = vueData + "という文字を、Springで確かに受信しました。";
		
		responseData.put("data1", returnText);
		responseData.put("comp", rndNum +"");
		return responseData;
	}
}
