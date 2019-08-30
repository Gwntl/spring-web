package org.mine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.bean.BeanUtil;
import org.mine.aplt.support.dao.BastDaoSupport;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.TuserDao;
import org.mine.model.Tuser;
import org.springframework.stereotype.Repository;

@Repository
public class TuserDaoImpl extends BastDaoSupport implements TuserDao{

//	@Autowired
//	SqlSessionTemplate session;
	
	@Override
	public List<Tuser> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tuser> getAllUsers() {
		// TODO Auto-generated method stub
		return getSqlSessionTemplate().selectList("userDao.select");
	}

	@Override
	public List<String> getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuser getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuser isExist(String username, String password , boolean nullException) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("password", password);
		Tuser tuser = getSqlSessionTemplate().selectOne("userDao.isExist",map);
		if(tuser == null && nullException){
			throw GitWebException.GIT_NOTFOUNT("userDao.isExist", CommonUtils.toString(map));
		}
		return tuser;
	}

	@Override
	public int insertOne(Tuser tuser) {
		return getSqlSessionTemplate().insert("userDao.insertOne", tuser);
	}

}
