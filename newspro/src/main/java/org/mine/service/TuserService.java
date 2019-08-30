package org.mine.service;

import java.util.List;

import org.mine.model.Tuser;

public interface TuserService {

	public List<Tuser> getUsers();

	public List<Tuser> getAllUsers();

	public List<String> getNames();

	public Tuser getUser(String username);
	
	public boolean isExist(String username, String password);
	
	public int insertOne(Tuser tuser);
}
