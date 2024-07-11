package io.renren.modules.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//每个消息都头信息，有发送人唯一号和接受人唯一号和消息 根据发送人的唯一号来存对话通道，这样就可以自由通话了。
public class MyServerTwoHandler extends ChannelInboundHandlerAdapter {
    // 保存所有连接的客户端
    private static final Map<String, ChannelHandlerContext> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立事件 当客户端因网络问题断开连接后，一旦网络恢复并重新连接，服务器端会自动触发 channelActive() 方法
     * 这个时候就发个消息给客户端，然后客户端发个唯一性的标识过来，然后就可以用这个唯一号把缓存的消息去检测一遍，把累积的消息发过去
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接：" + ctx.channel().remoteAddress());
        // 存储客户端的ChannelHandlerContext，以便后续消息推送
        clients.put(getClientId(ctx), ctx);
    }

    @Override //连接关闭事件
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接：" + ctx.channel().remoteAddress());
        // 移除断开连接的客户端
        clients.remove(getClientId(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端消息：" + msg);
        // 这里简单地将收到的消息发送给所有其他客户端
        for (Map.Entry<String, ChannelHandlerContext> entry : clients.entrySet()) {
            if (!entry.getValue().equals(ctx)) { // 排除发送者自己
                entry.getValue().writeAndFlush(msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 移除断开连接的客户端
        clients.remove(getClientId(ctx));
        cause.printStackTrace();
        ctx.close();
    }

    // 获取客户端的唯一标识，这里简单使用客户端的远程地址
    private String getClientId(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().asLongText();
        return ctx.channel().remoteAddress().toString();
    }

}
