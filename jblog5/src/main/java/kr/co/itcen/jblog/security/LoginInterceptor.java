package kr.co.itcen.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.jblog.service.UserService;
import kr.co.itcen.jblog.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setPassword(password);
		
		// 어플리케이션 어디서든지 Spring Container(ApplicationContext)를
		// 가져오는 방법
		// ApplicationContext appCtxt = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		// UserService userService = appCtxt.getBean(UserService.class);
		
		UserVo authUser = userService.getUser(vo);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		//session 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		System.out.println(authUser);
		
		response.sendRedirect(request.getContextPath());	
		return false;
	}
}