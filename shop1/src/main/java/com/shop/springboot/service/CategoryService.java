package com.shop.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.springboot.mapper.CategoryMapper;
import com.shop.springboot.vo.Category;


@Service
@Transactional
public class CategoryService {
	// 注入CategoryDao
	@Resource
	private CategoryMapper categoryMapper;

	// 业务层查询所有一级分类的方法
	public List<Category> findAll() {
		return categoryMapper.findAll();
	}

	// 业务层保存一级分类的操作
	public void save(Category category) {
		categoryMapper.save(category);
	}

	// 业务层根据一级分类id查询一级分类
	public Category findByCid(Integer cid) {
		return categoryMapper.findByCid(cid);
	}

	// 业务层删除一级分类
	public void delete(Category category) {
		categoryMapper.delete(category.getCid());
	}

	// 业务层修改一级分类
	public void update(Category category) {
		categoryMapper.update(category);
	}
	
}
