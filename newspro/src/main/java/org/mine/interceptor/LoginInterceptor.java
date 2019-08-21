package org.mine.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	private String[] aollwUrls;

	public void setAollwUrls(String[] aollwUrls) {
		this.aollwUrls = aollwUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String url = request.getRequestURL().toString();
		String sendUrl = url.substring(url.lastIndexOf(request.getContextPath())).replace(request.getContextPath(), "");
		System.out.println(sendUrl);
		
		MDC.put("trade", sendUrl.substring(1, sendUrl.indexOf(".")));
		
		if(sendUrl.equals("/login.do")){
			return true;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>clear");
		MDC.clear();
	}

}
