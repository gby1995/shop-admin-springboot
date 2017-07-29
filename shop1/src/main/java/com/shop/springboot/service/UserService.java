package com.shop.springboot.service;


import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.springboot.mapper.UserMapper;
import com.shop.springboot.utils.PageBean;
import com.shop.springboot.utils.UUIDUtils;
import com.shop.springboot.vo.User;



@Service
@Transactional
public class UserService {
	
	@Resource
	private UserMapper userMapper;
	
	
	// 按用户名查询用户的方法:
	public User findByUsername(String username){
		return userMapper.findByUsername(username);
	}

	// 业务层完成用户注册代码:
	public void save(User user) {
		// 将数据存入到数据库
		user.setState(1); // 0:代表用户未激活.  1:代表用户已经激活.
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userMapper.save(user);
		// 发送激活邮件;
		//MailUitls.sendMail(user.getEmail(), code);
	}

	// 业务层根据激活码查询用户
	public User findByCode(String code) {
		return userMapper.findByCode(code);
	}

	// 修改用户的状态的方法
	public void update(User existUser) {
		userMapper.update(existUser);
	}

	// 用户登录的方法
	public User login(User user) {
		return userMapper.login(user);
	}


	public User findByUid(Integer uid) {
		return userMapper.findByUid(uid);
	}


	public void delete(Integer id) {
		userMapper.delete(id);
	}
	
	// 业务层用户查询所有
     public PageBean<User> findByPage(Integer page) {
		PageBean<User> pageBean = new PageBean<User>();
		// 设置当前页数:
		pageBean.setPage(page);
		// 设置每页显示记录数:
		// 显示5个
		int limit = 5;
		pageBean.setLimit(limit);
		// 设置总记录数:
		int totalCount = 0;
		totalCount = userMapper.findCount();
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
		List<User> list = userMapper.findByPage(begin,limit);
		pageBean.setList(list);
		return pageBean;
	}
}
