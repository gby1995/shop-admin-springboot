package com.shop.springboot.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.OrderService;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.Order;
import com.shop.springboot.vo.OrderItem;

@Controller
@RequestMapping("/admin")
public class AdminOrderController{
	
	// 注入OrderService
	@Resource
	private OrderService orderService;

	// 提供后台查询所有订单的方法:
	@RequestMapping("/order/findAll")
	public String findAll(HttpServletRequest request, Integer page){
		// 订单的分页查询
		PageBean<Order> pageBean = orderService.findAll(page);
		// 将数据存入到值栈中保存到页面
		request.setAttribute("pageBean", pageBean);
		// 页面跳转:
		return "admin/order/list";
	}

	// 修改订单状态
	@RequestMapping("/order/updateState")
	public String updateState(Order order){
		// 根据id查询订单
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(3);
		orderService.update(currOrder);
		// 页面跳转
		return "list";
	}
	
	// 根据订单的id查询订单项:
	@RequestMapping("/order/findOrderItem")
	public String findOrderItem(Integer oid, HttpSession session){
		// 根据订单id查询订单项:
		List<OrderItem> list = orderService.findOrderItem(oid);
		// 显示到页面:
		session.setAttribute("list", list);
		// 页面跳转
		return "admin/order/orderItem";
	}
}
