package com.shop.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.springboot.vo.User;

@Mapper
public interface UserMapper {

	    // 按用户名查询用户的方法:
		User findByUsername(String username);
		
		int findCount();

		// 业务层完成用户注册代码:
		void save(User user);

		// 业务层根据激活码查询用户
	    User findByCode(String code);

		// 修改用户的状态的方法
		void update(User existUser);

		// 用户登录的方法
		User login(User user);

		// 业务层用户查询所有
		List<User> findByPage(@Param("begin") Integer begin,@Param("limit") Integer limit);


		User findByUid(Integer uid);


		void delete(Integer uid);
}