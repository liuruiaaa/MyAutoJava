package com.testblog.biz;
import com.testblog.entity.Message;
import com.testblog.tool.Pager;

import java.util.List;

public interface MessageBiz {
    int insert(Message ms);
    Message select(int id);
    void update(Message ms);
    void delete(int id);
    List<Message> getAll(int user_id, Pager page);

}
