package com.testblog.dao;
import com.testblog.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.testblog.tool.Pager;

@Repository("MessageDao")
public interface MessageDao {
    int insert(Message message);
    void update(Message message);
    void delete(int id);
    Message select(int id);
    List<Message> searchMessage(@Param("user_id") int user_id,@Param("pager")  Pager pager);
    int count(@Param("user_id")  int user_id );
}
