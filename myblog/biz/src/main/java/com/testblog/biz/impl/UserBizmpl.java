package com.testblog.biz.impl;

import com.testblog.biz.UserBiz;
import com.testblog.dao.UserDao;
import com.testblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserBiz")
public class UserBizmpl implements UserBiz {

    @Autowired
    public UserDao userDao;

    public User login(String username, String password) {
        User user = userDao.selectByUsername(username);
//        System.out.println(user);
//        System.out.println("===");
//        System.out.println(user.getPassword());
        if(user!=null && user.getPassword().equals(password)){
            return  user;
        }
        System.out.println(password);
        System.out.println("123");
        return null;
    }

    public void changePassword(User user) {
        userDao.update(user);
    }

    public User select(int id) {
        return userDao.select(id);
    }
    public  void update(User user){
        userDao.update(user);
    }
}
