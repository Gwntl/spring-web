package org.mine.controller.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.BatchRunJobDetail;
import org.mine.model.BatchRunQueue;
import org.mine.model.Tuser;
import org.mine.service.BatchRunQueueService;
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
	
	@Autowired
	private BatchRunQueueService queueService;
	
	public void testTransAOP(){
		logger.debug("RegisterUserController begin>>>>>>>>>>>>>>>>");
		BatchRunQueue queue = new BatchRunQueue();
		queue.setQueueId("1003");
		queue.setQueueName("TEST_RUN_3");
		queue.setQueueCrontrigger("15,55 * * ? * *");
		queue.setQueueJobId(1002L);
		queue.setQueueRemark("每分钟的15秒,55秒执行一次");
		
		List<BatchRunJobDetail> details = new ArrayList<>();
		BatchRunJobDetail detail = null;
		for(int i = 0; i < 13; i++){
			detail = new BatchRunJobDetail();
			detail.setJobId(1000L + i);
			detail.setJobName("test_" + i);
			details.add(detail);
		}
		
		queueService.batchRunInfo(queue, details);
		logger.debug("RegisterUserController end>>>>>>>>>>>>>>>>");
	}
}
