package com.mySns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * vue.js用
	 */
	@ResponseBody
	@CrossOrigin
	@RequestMapping(path = "/vueLogin/{loginId}/{password}", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
	public Map<String, String> vuepractice(@PathVariable String loginId, @PathVariable String password) {

		// SQL発行（ログイン）
		List<Map<String, Object>> list;
		list = jdbcTemplate.queryForList("SELECT * FROM user WHERE user_name = ? AND password = ?", loginId, password);

		// vueに返すデータ
		Map<String, String> responseData = new HashMap<String, String>();

		// 件数チェックして表示するコンポーネント名をreturnする。
		if (list.size() > 0) {
			responseData.put("compName", "MyTodo");
			return responseData;
		} else {
			responseData.put("compName", "MyLogin");
			return responseData;
		}
	}
	
	@ResponseBody
	@CrossOrigin
	@RequestMapping(path = "/vueLogin/todo", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
	public Map<String, Object> saveTodo(@RequestParam("title") String tittle, @RequestParam("honbun")String honbun) {

		// SQL発行（ログイン）
		List<Map<String, Object>> list;
		jdbcTemplate.update("INSERT INTO TODO VALUES(?,?)", tittle, honbun);

		// vueに返すデータ
		
		list = jdbcTemplate.queryForList("SELECT * FROM todo");
		Map<String, Object> responseData = new HashMap<String, Object>();

		// 件数チェックして表示するコンポーネント名をreturnする。
		if (list.size() > 0) {
			System.out.println("good");
			responseData.put("compName", "MyTodo");
			responseData.put("todo", list);
			return responseData;
		} else {
			responseData.put("compName", "MyLogin");
			return responseData;
		}
	}
}