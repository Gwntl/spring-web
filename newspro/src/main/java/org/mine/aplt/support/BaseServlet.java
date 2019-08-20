package org.mine.aplt.support;

import java.util.Map;

public class BaseServlet {
	
	public Map<String, String> context;

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}
}
