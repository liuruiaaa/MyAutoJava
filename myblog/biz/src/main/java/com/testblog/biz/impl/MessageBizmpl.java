package com.testblog.biz.impl;


import java.text.SimpleDateFormat;
import com.testblog.biz.MessageBiz;
import com.testblog.dao.MessageDao;
import com.testblog.entity.Message;
import com.testblog.tool.Pager;
import com.testblog.tool.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MessageBiz")
public class MessageBizmpl implements MessageBiz {

    @Autowired
    public MessageDao messageDao;

    public int insert(Message message) {


        return messageDao.insert( message);
    }
    public Message select(int id) {
        return messageDao.select(id);
    }
    public void update(Message message) {
         messageDao.update(message);
    }
    public void  delete(int id) {
         messageDao.select(id);
    }
    public List<Message> getAll(int user_id, Pager pager){

          return messageDao.searchMessage(user_id,pager);
    }
}
