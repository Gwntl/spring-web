package org.mine.dao;

import java.util.List;

import org.mine.model.Tuser;
import org.springframework.stereotype.Repository;

//@Repository
public interface TuserDao {
	
	public List<Tuser> select();
	
	public List<Tuser> getAllUsers();
	
	public List<String> getNames();
	
	public Tuser getUser(String username);
	
	public Tuser isExist(String username, String password, boolean nullException);
}
