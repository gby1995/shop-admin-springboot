package com.shop.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shop.springboot.vo.Product;


@Mapper
public interface ProductMapper {

	    // 首页上热门商品查询
		 List<Product> findHot();
		 
		 int findCount();
		 
		 int findCountCsid(Integer csid);
		 
		 int findCountCid(Integer cid);

		// 首页上最新商品的查询
		 List<Product> findNew();

		// 根据商品ID查询商品
		 Product findByPid(Integer pid) ;

		// 根据一级分类的cid带有分页查询商品
		 List<Product> findByPageCid(@Param("cid") Integer cid,@Param("begin") Integer begin,@Param("limit") Integer limit) ;

		// 根据二级分类查询商品信息
		 List<Product> findByPageCsid(@Param("csid") Integer csid,@Param("begin") Integer begin,@Param("limit") Integer limit);

		// 后台查询所有商品带分页
		 List<Product> findByPage(@Param("begin") Integer begin,@Param("limit") Integer limit) ;

		// 业务层保存商品方法:
		 void save(Product product);

		// 业务层删除商品
		 void delete(Integer pid) ;

		// 业务层修改商品的方法
		 void update(Product product) ;
}