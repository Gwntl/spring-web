package org.mine.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/helloWorld")
public class HelloWorldController {

	@RequestMapping("/view.do")
	public String showViews(){
		return "show";
	}
	
	@RequestMapping("/view1.do")
	public ModelAndView showViews_1(){
		return new ModelAndView("show1");
		
	}
	
	@RequestMapping("/htmlview.do")
	public String showHtmlViews(){
		return "htmlView";
	}
	
}
