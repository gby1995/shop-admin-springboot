package com.shop.springboot.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.CategoryService;
import com.shop.springboot.vo.Category;


@Controller
@RequestMapping("/admin")
public class AdminCategoryController{
	
	// 注入一级分类的Service
	@Resource
	public CategoryService categoryService;


	// 查询所有一级分类
	@RequestMapping("/category/findAll")
	public String findAll(HttpServletRequest request){
		// 调用Service查询所有一级分类
		List<Category> cList = categoryService.findAll();
		// 通过值栈保存一级分类集合:
		request.setAttribute("cList", cList);
		return "admin/category/list";
	}
	
	// 保存一级分类的方法
	@RequestMapping("/category/save")
	public String save(Category category){
		// 调用Service完成保存一级分类
		categoryService.save(category);
		// 进行页面跳转:
		return "admin/category/list";
	}
	
	// 删除一级分类的方法:
	@RequestMapping("/category/delete")
	public String delete(Category category){
		// 调用Service完成 一级分类的删除
		// 级联删除一定先查询在删除:
		category = categoryService.findByCid(category.getCid());
		categoryService.delete(category);
		// 进行页面转向:
		return "admin/category/list";
	}
	
	// 编辑一级分类的方法:
	@RequestMapping("/category/edit")
	public String edit(Category category , Model model){
		// 接收cid:
		// 根据cid进行查询:
		category = categoryService.findByCid(category.getCid());
		model.addAttribute("category",category);
		// 完成页面转向:将一级分类数据显示到页面上.
		return "admin/category/edit";
	}
	
	// 修改一级分类的方法:
	@RequestMapping("/category/update")
	public String update(Category category){
		// 使用模型驱动接收前台提交数据:
		categoryService.update(category);
		// 页面跳转:
		return "admin/category/list";
	}
	
	@RequestMapping("/category/add")
	public String add(){
		return "admin/category/add";
	}
}
