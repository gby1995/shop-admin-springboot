package com.shop.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shop.springboot.vo.Category;


@Mapper
public interface CategoryMapper {

	 List<Category> findAll();
	

	 void save(Category category);


	 Category findByCid(Integer cid);


	 void delete(Integer cid);
	

	 void update(Category category);
	
}