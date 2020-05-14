23
https://raw.githubusercontent.com/mrchengwenlong/NettyIM/master/im_lib/src/main/java/com/takiku/im_lib/internal/handler/MessageRespChannelHandler.java
package com.takiku.im_lib.internal.handler;



import com.google.protobuf.GeneratedMessageV3;

import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息响应，面向服务端发过来的状态回应
 */
public class MessageRespChannelHandler extends ChannelInboundHandlerAdapter {

    MessageRespHandler messageRespHandler;
    onResponseListener listener;
    public MessageRespChannelHandler(MessageRespHandler messageRespHandler,
                                     onResponseListener listener ){
        this.messageRespHandler = messageRespHandler;
        this.listener=listener;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          if (msg==null){
              return;
          }
          if (messageRespHandler!=null){
              if (messageRespHandler.isResponse(msg)){
                  listener.onResponse(messageRespHandler.tagResponse((GeneratedMessageV3) msg),msg);
              }else {
                  ctx.fireChannelRead(msg);
              }
          }else {
              ctx.fireChannelRead(msg);
          }
    }

  public   interface onResponseListener{
      void   onResponse(String tag,Object msg) throws IOException;
    }
}
