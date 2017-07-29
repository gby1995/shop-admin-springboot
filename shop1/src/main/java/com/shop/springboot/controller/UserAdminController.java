package com.shop.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.UserService;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.User;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
	
	// 注入用户的Service
	@Resource
	private UserService userService;

	// 后台查询所有用户的方法带分页:
	@RequestMapping("/user/findAll")
	public String findAll(HttpServletResponse response, HttpServletRequest request, Integer page) throws Exception{
		PageBean<User> pageBean = userService.findByPage(page);
		request.setAttribute("pageBean", pageBean);
		//request.getRequestDispatcher("/admin/user/list.html").forward(request, response);
		return "admin/user/list";
	}
	
	// 后台用户的删除
	@RequestMapping("/user/delete")
	public void delete(HttpServletRequest request, User user, HttpServletResponse response) throws Exception{
		userService.delete(user.getUid());
		request.getRequestDispatcher("/admin/user/findAll?page=1").forward(request, response);
		
	}
	
	// 后台用户的编辑
	@RequestMapping("/user/edit")
	public String edit(HttpSession session,User user) throws Exception{
		user = userService.findByUid(user.getUid());
		session.setAttribute("user", user);
		//request.getRequestDispatcher("/admin/user/edit.jsp").forward(request, response);
		return "admin/user/edit";
	
	}
	
	// 后台用户的修改:
	@RequestMapping("/user/update")
	public void update(HttpServletResponse response, User user) throws Exception{
		userService.update(user);
		response.sendRedirect("/shop/admin/user/findAll?page=1");
	}
}
