package com.shop.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.shop.springboot.vo.AdminUser;

@Mapper
public interface AdminUserMapper {
	 /**
	  * 根据用户名密码查询用�?
	  */
	
	
	AdminUser selectUserByPwd(AdminUser adminUser);
}