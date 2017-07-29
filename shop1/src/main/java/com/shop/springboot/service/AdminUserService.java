package com.shop.springboot.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.springboot.mapper.AdminUserMapper;
import com.shop.springboot.vo.AdminUser;


@Service
@Transactional
public class AdminUserService {
	@Resource
	private AdminUserMapper adminUserMapper;
	
	public AdminUser selectUserByPwd(AdminUser adminUser) {
		return adminUserMapper.selectUserByPwd(adminUser);
	}
	
}
