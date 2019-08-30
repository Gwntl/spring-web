package org.mine.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mine.service.TuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆控制器
 * 
 * @filename LoginController.java 
 * @author wzaUsers
 * @date 2019年8月21日下午5:20:02 
 * @version v1.0
 */
@Controller
public class LoginController {
	
	@Autowired
	private TuserService userService;

	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody
	//@ResponseBody将结果直接写入HTTP响应正文中,不执行页面跳转.
	public Map<String, String> login(@RequestParam(value = "username")String username,
			@RequestParam(value = "password") String password) {
		Map<String, String> map = new HashMap<>();
		System.out.println("username = " + username + ", password = " + password);
		
		if(userService.isExist(username, password)){
			map.put("username", username);
			map.put("password", password);
			map.put("result", "success");
			System.out.println(map.get("result"));
			return map;
		}
		map.put("result", "fail");
		System.out.println(map.get("result"));
		return map;
	}
	
	
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	@ResponseBody
	//@ResponseBody将结果直接写入HTTP响应正文中,不执行页面跳转.
	public Map<String, String> login(@RequestParam(value = "username")String username,
			@RequestParam(value = "password") String password, HttpSession seeion) {
		Map<String, String> map = new HashMap<>();
		System.out.println("username = " + username + ", password = " + password + ">>>>9383839");
		
		if(userService.isExist(username, password)){
			map.put("username", username);
			map.put("password", password);
			map.put("result", "success");
			System.out.println(map.get("result"));
			return map;
		}
		map.put("result", "fail");
		System.out.println(map.get("result"));
		return map;
	}

}
