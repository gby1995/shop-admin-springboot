package com.shop.springboot.controller;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.springboot.service.OrderService;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.Cart;
import com.shop.springboot.vo.CartItem;
import com.shop.springboot.vo.Order;
import com.shop.springboot.vo.OrderItem;
import com.shop.springboot.vo.User;

@Controller
@RequestMapping("/order")
public class OrderController {

	// 注入OrderService
	@Resource
	private OrderService orderService;

	// 生成订单的执行的方法:
	@RequestMapping("/saveOrder")
	public String saveOrder(HttpSession session, Order order) {

	
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			session.setAttribute("message", "亲!您还没有购物!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// 设置订单的状态
		order.setState(1); // 1:未付款.
		// 设置订单时间
		order.setOrdertime(new Date());
		// 设置订单关联的客户:
		User existUser = (User) session.getAttribute("existUser");
		if (existUser == null) {
			session.setAttribute("message", "亲!您还没有登录!");
			return "msg";
		}
		order.setUser(existUser);
		// 设置订单项集合:
		for (CartItem cartItem : cart.getCartItems()) {
			// 订单项的信息从购物项获得的.
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		orderService.save(order);
		// 清空购物车:
		cart.clearCart();

		// 页面需要回显订单信息:
		session.setAttribute("order", order);

		return "order";
	}

	// 查询我的订单:
	@RequestMapping("/findByUid")
	public String findByUid(HttpSession session, Integer page) {
		// 获得用户的id.
		User existUser = (User) session.getAttribute("existUser");
		// 获得用户的id
		Integer uid = existUser.getUid();
		// 根据用户的id查询订单:
		PageBean<Order> list = orderService.findByUid(uid, page);
		// 将PageBean数据带到页面上.
		session.setAttribute("pageBean", list);
		return "order";
	}

	// 根据订单id查询订单:
	@RequestMapping("/findByOid")
	public String findByOid(Order order) {
		order = orderService.findByOid(order.getOid());
		return "order";
	}

//	// 为订单付款:
//	public String payOrder(Order order) throws IOException {
//		// 1.修改数据:
//		Order currOrder = orderService.findByOid(order.getOid());
//		currOrder.setAddr(order.getAddr());
//		currOrder.setName(order.getName());
//		currOrder.setPhone(order.getPhone());
//		// 修改订单
//		orderService.update(currOrder);
//		// 2.完成付款:
//		// 付款需要的参数:
//		String p0_Cmd = "Buy"; // 业务类型:
//		String p1_MerId = "10001126856";// 商户编号:
//		String p2_Order = order.getOid().toString();// 订单编号:
//		String p3_Amt = "0.01"; // 付款金额:
//		String p4_Cur = "CNY"; // 交易币种:
//		String p5_Pid = ""; // 商品名称:
//		String p6_Pcat = ""; // 商品种类:
//		String p7_Pdesc = ""; // 商品描述:
//		String p8_Url = "http://192.168.36.69:8080/shop/order_callBack.action"; // 商户接收支付成功数据的地址:
//		String p9_SAF = ""; // 送货地址:
//		String pa_MP = ""; // 商户扩展信息:
//		String pd_FrpId = this.pd_FrpId;// 支付通道编码:
//		String pr_NeedResponse = "1"; // 应答机制:
//		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
//		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
//				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
//				pd_FrpId, pr_NeedResponse, keyValue); // hmac
//		// 向易宝发送请求:
//		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
//		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
//		sb.append("p1_MerId=").append(p1_MerId).append("&");
//		sb.append("p2_Order=").append(p2_Order).append("&");
//		sb.append("p3_Amt=").append(p3_Amt).append("&");
//		sb.append("p4_Cur=").append(p4_Cur).append("&");
//		sb.append("p5_Pid=").append(p5_Pid).append("&");
//		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
//		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
//		sb.append("p8_Url=").append(p8_Url).append("&");
//		sb.append("p9_SAF=").append(p9_SAF).append("&");
//		sb.append("pa_MP=").append(pa_MP).append("&");
//		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
//		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
//		sb.append("hmac=").append(hmac);
//		
//		// 重定向:向易宝出发:
//		ServletActionContext.getResponse().sendRedirect(sb.toString());
//		return NONE;
//	}

//	// 付款成功后跳转回来的路径:
//	public String callBack(){
//		// 修改订单的状态:
//		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
//		// 修改订单状态为2:已经付款:
//		currOrder.setState(2);
//		orderService.update(currOrder);
//		this.addActionMessage("支付成功!订单编号为: "+r6_Order +" 付款金额为: "+r3_Amt);
//		return "msg";
//	}
	
	// 修改订单的状态:
	@RequestMapping("/updateState")
	public String updateState(Order order){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}
}
