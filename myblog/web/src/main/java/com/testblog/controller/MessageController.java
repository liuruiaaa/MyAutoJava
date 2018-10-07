package com.testblog.controller;

import com.testblog.tool.Common;
import com.testblog.tool.Pager;
import com.testblog.biz.MessageBiz;
import com.testblog.biz.UserBiz;
import com.testblog.dao.MessageDao;
import com.testblog.entity.Message;
import com.testblog.entity.User;
import com.testblog.tool.createImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller("MessageController")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    public UserBiz userBiz;
    @Autowired
    public MessageDao messageDao;
    @Autowired
    public MessageBiz messageBiz;

    @RequestMapping("/page")
    public String page(Map<String,Object> map,HttpServletRequest request, HttpSession session)  {
        User user = (User)session.getAttribute("user");
        // 考虑分页查询
        Pager pager=new Pager();
        // 看是否传入了分页参数的页码
        String pageIndex = request.getParameter("pageIndex");
        int userId = (request.getParameter("userId")==null || request.getParameter("userId").isEmpty())?0: new Integer(request.getParameter("userId")).intValue();
        System.out.println(userId);
        System.out.println("============");
        if(!StringUtils.isEmpty(pageIndex)){
            int pSize = Integer.valueOf(pageIndex);
            pager.setPageIndex(pSize);
        }

        int count = messageDao.count(userId);
        pager.setTotalCount(count);
        int last = count % pager.getPageSize() == 0 ? (count / pager.getPageSize()) : ((count / pager.getPageSize() + 1));
        map.put("list",messageBiz.getAll(userId,pager));
        map.put("pager" ,pager);
        map.put("last" ,last);
        map.put("user",user);
        return "message_list";
    }

    @RequestMapping("/to_add_msg")
    public  String to_add_msg(HttpSession session,Map<String,Object> map){
        User user = (User)session.getAttribute("user");
        map.put("message",new Message());
        map.put("user",user);
        return "add_message";
    }

    @RequestMapping("/add_msg")
    public  String add_msg(Message message,HttpSession session){
        User user = (User)session.getAttribute("user");
        message.setUsername(user.getUsername());
        message.setUser_id(user.getId());
        message.setCreate_time(Common.getSqlDate());
        messageDao.insert(message);
        return "redirect:page";  //重定向到list控制器中
    }


}
