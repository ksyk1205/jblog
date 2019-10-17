package kr.co.itcen.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping({"","/main"})
	public String index() {
		return "main/index";
	}

}
