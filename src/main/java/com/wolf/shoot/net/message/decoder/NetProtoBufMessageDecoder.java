package com.wolf.shoot.net.message.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jiangwenping on 17/2/3.
 */

public class NetProtoBufMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Charset charset;

    private INetProtoBufMessageDecoderFactory iNetMessageDecoderFactory;

    public NetProtoBufMessageDecoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageDecoderFactory = new NetProtoBufMessageDecoderFactory();
    }

    public NetProtoBufMessageDecoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }



    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(iNetMessageDecoderFactory.praseMessage(msg));
    }
}

