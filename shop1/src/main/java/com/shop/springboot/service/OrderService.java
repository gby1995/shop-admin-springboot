package com.shop.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.springboot.mapper.OrderMapper;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.vo.Order;
import com.shop.springboot.vo.OrderItem;


@Service
@Transactional
public class OrderService {
	// 注入OrderDao
	@Resource
	private OrderMapper orderMapper;


	// 业务层根据用户id查询订单,带分页查询.
		public PageBean<Order> findByUid(Integer uid,Integer page) {
			PageBean<Order> pageBean = new PageBean<Order>();
			// 设置当前页数:
			pageBean.setPage(page);
			// 设置每页显示记录数:
			// 显示5个
			int limit = 5;
			pageBean.setLimit(limit);
			// 设置总记录数:
			int totalCount = 0;
			totalCount = orderMapper.findCountByUid(uid);
			pageBean.setTotalCount(totalCount);
			// 设置总页数
			int totalPage = 0;
			if(totalCount % limit == 0){
				totalPage = totalCount / limit;
			}else{
				totalPage = totalCount / limit + 1;
			}
			pageBean.setTotalPage(totalPage);
			// 设置每页显示数据集合:
			int begin = (page - 1)*limit;
			List<Order> list = orderMapper.findPageByUid(uid,begin,limit);
			pageBean.setList(list);
			return pageBean;
		}
	// 根据订单id查询订单
	public Order findByOid(Integer oid) {
		return orderMapper.findByOid(oid);
	}

	// 业务层修改订单的方法:
	public void update(Order currOrder) {
		orderMapper.update(currOrder);
	}
	
	public void save(Order currOrder) {
		orderMapper.save(currOrder);
	}

	
	

	// 业务层查询订单项的方法
	public List<OrderItem> findOrderItem(Integer oid) {
		return orderMapper.findOrderItem(oid);
	}

	// 业务层查询所有订单方法
		public PageBean<Order> findAll(Integer page) {
			PageBean<Order> pageBean = new PageBean<Order>();
			// 设置参数
			pageBean.setPage(page);
			// 设置每页显示的记录数:
			int limit = 10;
			pageBean.setLimit(limit);
			// 设置总记录数
			int totalCount = orderMapper.findCount();
			pageBean.setTotalCount(totalCount);
			// 设置总页数
			int totalPage = 0;
			if(totalCount % limit == 0){
				totalPage = totalCount / limit;
			}else{
				totalPage = totalCount / limit + 1;
			}
			pageBean.setTotalPage(totalPage);
			// 设置每页显示数据集合
			int begin = (page - 1) * limit;
			List<Order> list = orderMapper.findByPage(begin,limit);
			pageBean.setList(list);
			return pageBean;
		}
	
}
