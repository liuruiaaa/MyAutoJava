package io.renren.modules.test.service.impl;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.renren.modules.test.netty.NettyConfig;
import io.renren.modules.test.service.PushMsgService;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * @author dongliang7
 * @projectName websocket-parent
 * @ClassName PushMsgServiceImpl.java
 * @description: 推送消息实现类
 * @createTime 2023年02月06日 16:45:00
 */
@Service
public class PushMsgServiceImpl implements PushMsgService {

    @Override
    public void pushMsgToOne(String userId, String msg) {
        Channel channel = NettyConfig.getChannel(userId);
        if (Objects.isNull(channel)) {
            throw new RuntimeException("未连接socket服务器");
        }
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public void pushMsgToAll(String msg) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}