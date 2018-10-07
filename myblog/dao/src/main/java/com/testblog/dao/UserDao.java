package com.testblog.dao;
import com.testblog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("UserDao")
public interface UserDao {
    int insert(User user);
    void update(User user);
    void delete(int id);
    User select(int id);
    User selectByCreateSn(String username);
    User selectByUsername(@Param("username") String username);
}