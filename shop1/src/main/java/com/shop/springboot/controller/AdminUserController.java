package com.shop.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.AdminUserService;
import com.shop.springboot.vo.AdminUser;


@Controller
@RequestMapping("/admin")
public class AdminUserController  {

	@Resource
	private AdminUserService adminUserService;

	// 后台登录的执行的方法
	@RequestMapping("/login")
	public String login(HttpServletResponse response, AdminUser adminUser, HttpServletRequest request, HttpSession session) throws Exception {
		// 调用service方法完成登录
		AdminUser existAdminUser = adminUserService.selectUserByPwd(adminUser);
		// 判断
		if (existAdminUser == null) {
			// 用户名或密码错误
			request.setAttribute("message","用户名或密码错误!");
			//response.sendRedirect("admin/index.html");
			return "admin/index";
		} else {
			// 登录成功:
			session.setAttribute("existAdminUser", existAdminUser);
			//response.sendRedirect("/shop/admin/home.jsp");
			return "admin/home";
		}
	}
}
