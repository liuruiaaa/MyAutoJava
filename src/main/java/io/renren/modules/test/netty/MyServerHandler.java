package io.renren.modules.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义的Handler需要继承Netty规定好的HandlerAdapter
 * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 * ChannelInboundHandlerAdapter(入站处理器)
 * ChannelOutboundHandler(出站处理器)
 *
 * ChannelInboundHandlerAdapter处理器常用的事件有：
 * 注册事件 fireChannelRegistered。
 * 连接建立事件 fireChannelActive。
 * 读事件和读完成事件 fireChannelRead、ChannelReadComplete。
 * 异常通知事件 fireExceptionCaught。
 * 用户自定义事件 fireEventTriggered。
 * Channel 可写状态变化事件 fireChannelWritabilityChanged。
 * 连接关闭事件 fireChannelInactive。
 * 都有对对应的
 *
 *ChannelOutboundHandler处理器常用的事件有：
 * 端口绑定 bind。
 * 连接服务端 connect。
 * 写事件 write。
 * 刷新时间 flush。
 * 读事件 read。
 * 主动断开连接 disconnect。
 * 关闭 channel 事件 close。
 *
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //获取客户端发送过来的消息
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        //获取到线程池eventLoop，添加线程，执行
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //长时间操作，不至于长时间的业务操作导致Handler阻塞
                    Thread.sleep(1000);
                    ByteBuf byteBuf = (ByteBuf) msg;
                    System.out.println("长时间的业务处理:"+"发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送消息给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个消息：哈哈。。。", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }
}
