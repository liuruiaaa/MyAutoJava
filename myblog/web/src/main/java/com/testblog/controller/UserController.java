package com.testblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.testblog.tool.createImage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import com.testblog.biz.UserBiz;
import com.testblog.biz.MessageBiz;
import com.testblog.entity.User;
import com.testblog.dao.UserDao;
import com.testblog.entity.Message;

@Controller("UserController")
@RequestMapping("/user")
public class UserController{
    @Autowired
    public UserBiz userBiz;
    @Autowired
    public UserBiz userDao;
    @Autowired
    public MessageBiz messageBiz;
    @RequestMapping("/to_login")
    public String toLogin()  {
        return "login";
    }

    @RequestMapping("/login")
    //session 只需要直接声明就可以用session了
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password){//声明一定帐号和密码
        User user = userBiz.login(username,password);
        if (user == null) {
            return "redirect:/user/to_login";
        }
        session.setAttribute("user",user);
        return "redirect:/message/page";
    }

    @RequestMapping("/get_img")
    public void getImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createImage img  = new createImage();
        img.service( request,  response);
    }

    @RequestMapping("/my_info")
    public  String  my_info(HttpSession session, Map<String,Object> map){

        User user_old = (User)session.getAttribute("user");
        User user = userBiz.select(user_old.getId());
        session.setAttribute("user",user);
        map.put("user" ,user);
        return "user";
    }
    @RequestMapping("/my_info_edit")
    public  String  my_info_edit(User user,HttpSession session){
        User userOld = (User)session.getAttribute("user");
        user.setId(userOld.getId());
        userBiz.update(user);
        return "redirect:/user/my_info";
    }

}
