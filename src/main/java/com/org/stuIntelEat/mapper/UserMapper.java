package com.org.stuIntelEat.mapper;

import com.org.stuIntelEat.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(User user);
}
