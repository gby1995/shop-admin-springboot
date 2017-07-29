package com.shop.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shop.springboot.service.CategorySecondService;
import com.shop.springboot.service.ProductService;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.CategorySecond;
import com.shop.springboot.vo.Product;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
	

	// 注入ProductService
	@Resource
	private ProductService productService;

	// 注入CategorySecondService
	@Resource
	private CategorySecondService categorySecondService;

	// 查询所有的商品:
	@RequestMapping("/product/findAll")
	public String findAll(HttpServletResponse response, HttpServletRequest request, Integer page) throws Exception, IOException {
		PageBean<Product> pageBean = productService.findByPage(page);
		// 将PageBean数据存入到值栈中.
		request.setAttribute("pageBean", pageBean);
		// 页面跳转
		//request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
		return "admin/product/list";
	}

	// 跳转到添加页面的方法:
	@RequestMapping("/product/addPage")
	public String addPage(HttpServletResponse response, HttpServletRequest request) throws Exception, IOException {
		// 查询所有的二级分类:
		List<CategorySecond> csList = categorySecondService.findAll();
		// 将二级分类的数据显示到页面上
		request.setAttribute("csList", csList);
		// 页面跳转
		//request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response);
		return "admin/product/add";
	}

	// 保存商品的方法:
	@RequestMapping("/product/save")
	public void save( HttpServletResponse response, File upload, String uploadFileName, Product product , HttpServletRequest request) throws IOException, Exception {
		// 将提交的数据添加到数据库中.
		product.setPdate(new Date());
		// product.setImage(image);
		if(upload != null){
			// 将商品图片上传到服务器上.
			// 获得上传图片的服务器端路径.
			String path = request.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			FileUtils.copyFile(upload, diskFile);
	
			product.setImage("products/" + uploadFileName);
		}
		productService.save(product);
		request.getRequestDispatcher("/admin/product/fiondAll?page=1").forward(request, response);
		
	}

	// 删除商品的方法:
	@RequestMapping("/product/delete")
	public void delete(HttpServletResponse response, Product product, HttpServletRequest request) throws Exception, IOException {
		// 根据id查询商品信息
		product = productService.findByPid(product.getPid());
		// 删除商品的图片:
		String path = request.getServletContext().getRealPath(
				"/" + product.getImage());
		File file = new File(path);
		file.delete();
		// 删除数据库中商品记录:
		productService.delete(product.getPid());
		// 页面跳转
		request.getRequestDispatcher("/admin/product/fiondAll?page=1").forward(request, response);
		
	}

	// 编辑商品的方法
	@RequestMapping("/product/edit")
	public String edit(Model model, Integer pid, HttpServletRequest request) throws Exception, IOException {
		// 根据商品id查询商品信息
		Product product = productService.findByPid(pid);
		model.addAttribute("model", product);
		// 查询所有二级分类
		List<CategorySecond> csList = categorySecondService.findAll();
		request.setAttribute("csList", csList);
		// 页面跳转到编辑页面:
		//request.getRequestDispatcher("/admin/product/fiondAll.htm?page=1").forward(request, response);
		return "admin/product/edit";
	}

	// 修改商品的方法
	@RequestMapping("/product/update")
	public void update(HttpServletResponse response, String uploadFileName, Product product, HttpServletRequest request, String upload) throws IOException, Exception {
		// 将信息修改到数据库
		product.setPdate(new Date());
		
		// 上传:
		if(upload != null ){
			String delPath = request.getServletContext().getRealPath(
					"/" + product.getImage());
			File file = new File(delPath);
			file.delete();
			// 获得上传图片的服务器端路径.
			String path = request.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			//FileUtils.copyFile(upload, diskFile);

			product.setImage("products/" + uploadFileName);
		}
		productService.update(product);
		// 页面跳转
		request.getRequestDispatcher("/admin/product/fiondAll?page=1").forward(request, response);
		
	}
}
