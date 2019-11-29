package com.java.freemarker.ftl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTest {
	public static void main(String[] args) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		cfg.setOutputEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File("src/test/resource/config/template/ftl"));
		
		Map<String, Object> root = new HashMap<>();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < 7; i ++){
			list.add(i);
		}
		
		Map<String, Address> map = new HashMap<>();
		map.put("1",new Address("中国","北京"));
		map.put("2",new Address("中国","北京"));
		map.put("3",new Address("中国","北京"));
		
		root.put("lis", list);
		root.put("map", map);
		root.put("user", "test-debug");
		root.put("subs", "01234");
		Template template = cfg.getTemplate("test.ftl");
		PrintWriter out = new PrintWriter(System.out);
		template.process(root, out);
		out.flush();
		out.close();
	}
}
