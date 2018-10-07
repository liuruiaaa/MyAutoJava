package com.testblog.biz;
import com.testblog.entity.User;

public interface UserBiz {
    User login(String sn,String password);
    User select(int id);
    void update(User user);
    void changePassword(User user);
}
