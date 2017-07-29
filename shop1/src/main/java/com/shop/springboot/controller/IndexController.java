package com.shop.springboot.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.CategoryService;
import com.shop.springboot.service.ProductService;
import com.shop.springboot.vo.Category;
import com.shop.springboot.vo.Product;

@Controller
public class IndexController{
	// 注入一级分类的Service:
	@Resource
	private CategoryService categoryService;
	// 注入商品的Service
	@Resource
	private ProductService productService;

	/**
	 * 执行的访问首页的方法:
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpSession session){
		// 查询所有一级分类集合
		List<Category> cList = categoryService.findAll();
		
		// 将一级分类存入到Session的范围:
		session.setAttribute("cList", cList);
		// 查询热门商品:
		List<Product> hList = productService.findHot();
		// 保存到值栈中:
		request.setAttribute("hList", hList);
		// 查询最新商品:
		List<Product> nList = productService.findNew();
		// 保存到值栈中:
		request.setAttribute("nList", nList);
		return "index";
	}
	
	
}
