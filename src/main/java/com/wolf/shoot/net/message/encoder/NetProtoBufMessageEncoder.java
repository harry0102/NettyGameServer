package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jiangwenping on 17/2/8.
 */
public class NetProtoBufMessageEncoder extends MessageToMessageEncoder<NetProtoBufMessage> {

    private final Charset charset;

    private INetProtoBufMessageEncoderFactory iNetMessageEncoderFactory;

    public NetProtoBufMessageEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = new NetProtoBufMessageEncoderFactory();
    }

    public NetProtoBufMessageEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NetProtoBufMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(netMessageBuf);
    }
}
