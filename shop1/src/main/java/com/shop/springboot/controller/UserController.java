package com.shop.springboot.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.UserService;
import com.shop.springboot.vo.User;


@Controller
@RequestMapping("/user")
public class UserController{
	
	// 注入UserService
	@Resource
	private UserService userService;


	/**
	 * 跳转到注册页面的执行方法
	 */
	@RequestMapping("/registPage")
	public String registPage() {
		return "regist";
	}

	/**
	 * AJAX进行异步校验用户名的执行方法
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/findByName")
	public void findByName(HttpServletResponse response, User user) throws IOException {
		// 调用Service进行查询:
		User existUser = userService.findByUsername(user.getUsername());
		// 获得response对象,项页面输出:
		response.setContentType("text/html;charset=UTF-8");
		// 判断
		if (existUser != null) {
			// 查询到该用户:用户名已经存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		} else {
			// 没查询到该用户:用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用</font>");
		}
	}

	/**
	 * 用户注册的方法:
	 */
	@RequestMapping("/regist")
	public String regist(HttpSession session, String checkcode, User user) {
		// 判断验证码程序:
		// 从session中获得验证码的随机值:
		String checkcode1 = (String)session.getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			session.setAttribute("message", "验证码输入错误!");
			return "checkcodeFail";
		}
		userService.save(user);
		session.setAttribute("message", "注册成功!请去邮箱激活!");
		return "msg";
	}

	/**
	 * 用户激活的方法
	 */
	@RequestMapping("/active")
	public String active(User user, HttpSession session) {
		// 根据激活码查询用户:
		User existUser = userService.findByCode(user.getCode());
		// 判断
		if (existUser == null) {
			// 激活码错误的
			session.setAttribute("message", "激活失败:激活码错误!");
		} else {
			// 激活成功
			// 修改用户的状态
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			session.setAttribute("message", "激活成功:请去登录!");
		}
		return "msg";
	}

	/**
	 * 跳转到登录页面
	 */
	@RequestMapping("/loginPage")
	public String loginPage() {
		return "login";
	}

	/**
	 * 登录的方法
	 * @throws Exception 
	 */
	@RequestMapping("/login")
	public void login(User user, HttpSession session, HttpServletResponse response) throws Exception {
		User existUser = userService.login(user);
		// 判断
		if (existUser == null) {
			// 登录失败
			session.setAttribute("message", "登录失败:用户名或密码错误或用户未激活!");
			response.sendRedirect("/shop/loginPage.htm");
		} else {
			// 登录成功
			// 将用户的信息存入到session中
			session.setAttribute("existUser", existUser);
			// 页面跳转
			response.sendRedirect("/shop/index.htm");
		}
	
	}
	
	/**
	 * 用户退出的方法
	 * @throws Exception 
	 * @throws ServletException 
	 */
	@RequestMapping("/quit")
	public void quit(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 销毁session
		session.invalidate();
		response.sendRedirect("/shop/index.htm");
	}

}
