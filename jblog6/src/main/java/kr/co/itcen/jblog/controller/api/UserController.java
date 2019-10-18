package kr.co.itcen.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.jblog.dto.JSONResult;
import kr.co.itcen.jblog.service.UserService;

@Controller("userAPiController")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	//id 중복체크 
	@ResponseBody
	@RequestMapping("/checkid")
	public JSONResult checkId(@RequestParam(value="id",required=true,defaultValue="")String id) {
		Boolean exist = userService.existUser(id);
		
		return JSONResult.success(exist);
		
	}
}
