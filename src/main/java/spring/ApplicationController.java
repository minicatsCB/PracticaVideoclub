package spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ApplicationController{
	@RequestMapping("/processForm")
	public ModelAndView process(@RequestParam String input){
		return new ModelAndView("result").addObject("result", input.toUpperCase());
	}
}