package com.jdbc;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.model.Tuser;
import org.mine.service.TuserService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/base/application-context-impl.xml"})
public class JdbcConnectToMysqlTest {

	@Autowired
	private TuserService service;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void test1(){
		System.out.println("ww'");
		String url = "jdbc:mysql://127.0.0.1:3306/msbadb?serverTimezone=UTC";
		String user = "msba";
		String password = "msba";
		try{
			System.out.println("connect ----");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("connect ----");
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("connect ----");
			String sql = "select * from t_user";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while(set.next()){
				int a = set.getInt(1);
				String b = set.getString(2);
				String c = set.getString(3);
				String d = set.getString(4);
				System.out.println(a + ", " + b + ", " + c + ", " + d);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		List<Tuser> list = service.getUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		List<String> list1 = service.getNames();
		System.out.println(list1.size());
		System.out.println(list1.get(0));
		
		System.out.println(service.getUser("test").toString());
	}
	
	@Test
	public void test3(){
		MDC.put("trade", "test");
		List<Tuser> list = service.getAllUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		System.out.println(service.isExist("test", "test"));
	}
}
