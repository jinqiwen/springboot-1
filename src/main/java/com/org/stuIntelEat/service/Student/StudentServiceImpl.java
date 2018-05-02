package com.org.stuIntelEat.service.Student;

import java.util.List;

import com.org.stuIntelEat.mapper.StudentMapper;
import com.org.stuIntelEat.pojo.ShopCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentMapper studentMapper;
	  
	  //将收藏，购买的菜品记录存入数据库
	  public int insertShopCart(ShopCart shopCart) {
		return  studentMapper.insertShopCart(shopCart);
	  }
	  //查询指定时间内用户收藏或购买的菜品
	  public List<ShopCart> allStuShoppingOrSave(ShopCart shopCart){
		return  studentMapper.allStuShoppingOrSave(shopCart); 
	  }
}
