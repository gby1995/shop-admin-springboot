package com.shop.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.springboot.vo.Order;
import com.shop.springboot.vo.OrderItem;


@Mapper
public interface OrderMapper {
		// 业务层保存订单的方法
		 void save(Order order);

		// 业务层根据用户id查询订单,带分页查询.
		 List<Order> findPageByUid(@Param("uid") Integer uid,@Param("begin") Integer begin,@Param("limit") Integer limit);

		// 根据订单id查询订单
		 Order findByOid(Integer oid);

		// 业务层修改订单的方法:
		 void update(Order currOrder) ;
		 
		 List<Order> findByPage(@Param("begin") Integer begin,@Param("limit") Integer limit);
		 
		 int findCount();
		 
		 int findCountByUid( Integer uid);

		// 业务层查询所有订单方法
		 List<Order> findAll(Integer page);

		// 业务层查询订单项的方法
		 List<OrderItem> findOrderItem(Integer oid) ;
}