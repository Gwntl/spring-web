package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.mine.model.Tuser;
import org.mine.service.TuserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class JdbcConnectToMysqlTest extends TestCase {

	
	public void jdbc(){
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
	
	public void testMtBatis(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/base/application-context.xml");
		TuserService service = context.getBean(TuserService.class);
		List<Tuser> list = service.getUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		List<String> list1 = service.getNames();
		System.out.println(list1.size());
		System.out.println(list1.get(0));
		
		System.out.println(service.getUser("test").toString());
	}
	
	public void testMtBatisImpl(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/base/application-context-impl.xml");
		TuserService service = context.getBean(TuserService.class);
		List<Tuser> list = service.getAllUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		System.out.println(service.isExist("test", "test"));
	}
}
