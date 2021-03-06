package com.wolf.shoot.service.net;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.logic.common.OnlineHeartMessage;
import com.wolf.shoot.net.session.NettyTcpSession;
import com.wolf.shoot.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.sesssion.NetTcpSessionLoopUpService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * Created by jiangwenping on 17/2/7.
 */
public class GameNetMessageSocketServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().get(NettyTcpSessionBuilder.class);
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(ctx.channel());
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().get(NetTcpSessionLoopUpService.class);
        netTcpSessionLoopUpService.addT(nettyTcpSession);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        Thread.sleep(1000L);
//        ByteBuf byteBuffer = (ByteBuf) msg;

//        System.out.println("服务端收到："+byteBuffer.array());
//        ctx.writeAndFlush(msg);
        NetProtoBufMessage netMessage = (NetProtoBufMessage) msg;
        if(netMessage instanceof OnlineHeartMessage){
            OnlineHeartMessage onlineHeartMessage = (OnlineHeartMessage) netMessage;
            System.out.println("服务端收到 OnlineHeartMessage id：" + onlineHeartMessage.getId());
        }


    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object var) throws Exception{
        super.userEventTriggered(ctx, var);
        if(var instanceof IdleStateEvent){
            //说明是空闲事件
            ctx.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

}
