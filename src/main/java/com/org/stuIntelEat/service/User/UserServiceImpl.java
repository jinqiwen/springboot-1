package com.org.stuIntelEat.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.org.stuIntelEat.mapper.UserMapper;
import com.org.stuIntelEat.pojo.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int insert(User user){
        return this.userMapper.insert(user);
    }
}
