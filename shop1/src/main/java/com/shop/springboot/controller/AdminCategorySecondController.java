package com.shop.springboot.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.CategorySecondService;
import com.shop.springboot.service.CategoryService;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.Category;
import com.shop.springboot.vo.CategorySecond;

@Controller
@RequestMapping("/admin")
public class AdminCategorySecondController{

	@Resource
	private CategorySecondService categorySecondService;
	// 注入一级分类的Service
	@Resource
	private CategoryService categoryService;


	// 带有分页的查询所有二级分类的操作:
	@RequestMapping("/categorySecond/findAll")
	public String findAll(HttpServletRequest request, Integer page) {
		// 调用Service进行查询.
		PageBean<CategorySecond> pageBean = categorySecondService
				.findByPage(page);
		// 将pageBean的数据存入到值栈中.
		request.setAttribute("pageBean", pageBean);
		return "admin/categorysecond/list";
	}

	// 跳转到页面的方法:
	@RequestMapping("/categorySecond/addPage")
	public String addPage(HttpServletRequest request) {
		// 查询所有一级分类.
		List<Category> cList = categoryService.findAll();
		// 将集合存入到值栈中.
		request.setAttribute("cList", cList);
		// 页面跳转:
		return "admin/categorysecond/add";
	}

	// 添加二级分类的方法:
	@RequestMapping("/categorySecond/save")
	public String save(CategorySecond categorySecond) {
		categorySecondService.save(categorySecond);
		return "admin/categorysecond/list";
	}

	// 删除二级分类的方法:
	@RequestMapping("/categorySecond/delete")
	public String delete(CategorySecond categorySecond) {
		categorySecondService.delete(categorySecond);
		return "admin/categorysecond/list";
	}

	// 编辑二级分类的方法:
	@RequestMapping("/categorySecond/edit")
	public String edit(HttpServletRequest request, Integer csid) {
		// 根据id查询二级分类:
		CategorySecond categorySecond = categorySecondService.findByCsid(csid);
		System.out.println(categorySecond.getCategory().getCid());
		request.setAttribute("cs", categorySecond);
		// 查询所有一级分类:
		List<Category> cList = categoryService.findAll();
		// 将集合存入到值栈中.
		request.setAttribute("cList", cList);
		// 页面跳转:
		return "admin/categorysecond/edit";
	}
	
	// 修改二级分类的方法:
	@RequestMapping("/categorySecond/update")
	public String update(CategorySecond categorySecond){
		categorySecondService.update(categorySecond);
		return "admin/categorysecond/list";
	}
}
