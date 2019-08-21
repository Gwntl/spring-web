package org.mine.service.impl;

import java.util.List;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.dao.TuserDao;
import org.mine.model.Tuser;
import org.mine.service.TuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TUserServiceImpl implements TuserService {
	private static final Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);
	
	@Autowired
	private TuserDao dao;

	@Override
	public List<Tuser> getUsers() {
		return dao.select();
	}

	@Override
	public List<Tuser> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public List<String> getNames() {
		return dao.getNames();
	}

	@Override
	public Tuser getUser(String username) {
		return dao.getUser(username);
	}

	@Override
	public boolean isExist(String username, String password) {
		// TODO Auto-generated method stub
		logger.info("isExist-------------");
		try{
			if(dao.isExist(username, password, true) != null){
				throw GitWebException.GIT1001("shibail>>>>>>>>>>>");
			}
		}catch(MineException e){
			logger.error("test: " + MineException.getStackTrace(e));
			throw GitWebException.GIT1001(e.getError_msg());
		}
		return true;
	}
}
