package com.org.stuIntelEat.service.Student;

import java.util.List;

import com.org.stuIntelEat.pojo.ShopCart;

public interface StudentService {
	 //将收藏，购买的菜品记录存入数据库
	  public int insertShopCart(ShopCart shopCart);
	  //查询指定时间内用户收藏或购买的菜品
	  public List<ShopCart> allStuShoppingOrSave(ShopCart shopCart);
}
