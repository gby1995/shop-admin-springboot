package com.shop.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.springboot.vo.CategorySecond;

@Mapper
public interface CategorySecondMapper {

	    // 二级分类带有分页的查询操作:
		 List<CategorySecond> findByPage(@Param("begin") Integer begin,@Param("limit") Integer limit);

		// 业务层的保存二级分类的操作
		 void save(CategorySecond categorySecond);

		 int findCount();
		 
		// 业务层的删除二级分类的操作
		 void delete(Integer csid);

		// 业务层根据id查询二级分类
		 CategorySecond findByCsid(Integer csid);
		 
		 CategorySecond findByCid(Integer cid);

		// 业务层修改二级分类的方法
		 void update(CategorySecond categorySecond);

		// 业务层查询所有二级分类(不带分页)
		 List<CategorySecond> findAll();
}