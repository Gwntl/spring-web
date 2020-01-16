package org.mine.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.Tuser;
import org.mine.service.TuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/register")
@Controller
public class RegisterUserController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterUserController.class);

	@Autowired
	private TuserService t_userservice;
	
	@RequestMapping(value = "/user.do",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> registerUser(@RequestParam(value="username") String username,
			@RequestParam(value="pasword") String password, @RequestParam(value="remark") String remark){
		Map<String, String> map = new HashMap<String, String>();
		
		
		if(CommonUtils.isEmpty(username) || CommonUtils.isEmpty(password)){
			throw GitWebException.GIT1002("用户名或密码");
		}
		
		if(t_userservice.isExist(username, password)){
			throw GitWebException.GIT1004(username);
		}
		
		Tuser tuser = new Tuser();
		tuser.setUsername(username);
		tuser.setPassword(password);
		tuser.setRemark(remark);
		t_userservice.insertOne(tuser);
		
		map.put("call_status", "0000");
		map.put("result", "新增用户成功");
		return map;
	}
}
