package com.shop.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.ProductService;
import com.shop.springboot.vo.Cart;
import com.shop.springboot.vo.CartItem;
import com.shop.springboot.vo.Product;


@Controller
@RequestMapping("/cart")
public class CartController {
	
	// 注入商品的Service
	@Resource
	private ProductService productService;

	// 将购物项添加到购物车:执行的方法
	@RequestMapping("/addCart")
	public String addCart(Integer count, Integer pid, HttpSession session) {
		// 封装一个CartItem对象.
		CartItem cartItem = new CartItem();
		// 设置数量:
		cartItem.setCount(count);
		// 根据pid进行查询商品:
		Product product = productService.findByPid(pid);
		// 设置商品:
		cartItem.setProduct(product);
		// 将购物项添加到购物车.
		// 购物车应该存在session中.
		Cart cart = getCart(session);
		cart.addCart(cartItem);
		return "cart";
	}

	// 清空购物车的执行的方法:
	@RequestMapping("/clearCart")
	public String clearCart(HttpSession session){
		// 获得购物车对象.
		Cart cart = getCart(session);
		// 调用购物车中清空方法.
		cart.clearCart();
		return "cart";
	}
	
	// 从购物车中移除购物项的方法:
	@RequestMapping("/removeCart")
	public String removeCart(Integer pid, HttpSession session){
		// 获得购物车对象
		Cart cart = getCart(session);
		// 调用购物车中移除的方法:
		cart.removeCart(pid);
		// 返回页面:
		return "cart";
	}
	
	// 我的购物车:执行的方法
	@RequestMapping("/myCart")
	public String myCart(){
		return "cart";
	}
	
	/**
	 * 获得购物车的方法:从session中获得购物车.
	 * @return
	 */
	private Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
}
