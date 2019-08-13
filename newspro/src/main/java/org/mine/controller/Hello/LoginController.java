package org.mine.controller.Hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, String> login(@RequestParam(value = "username")String username,
			@RequestParam(value = "password") String password) {
		Map<String, String> map = new HashMap<>();
		System.out.println("username = " + username + ", password = " + password);
		
		if(username.equals("admin") || password.equals("admin")){
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
