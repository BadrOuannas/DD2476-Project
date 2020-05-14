23
https://raw.githubusercontent.com/mrchengwenlong/NettyIM/master/im_lib/src/main/java/com/takiku/im_lib/internal/handler/MessageReceiveHandler.java
package com.takiku.im_lib.internal.handler;

/**
 * author:chengwl
 * Description:消息接收，来自其他客户端的消息,回调在子线程
 * Date:2020/4/18
 */
public interface MessageReceiveHandler<message extends com.google.protobuf.GeneratedMessageV3> {
    /**
     * 是否是用户发的消息
     * @param msg
     * @return
     */
    boolean isClientMessage(Object msg);

    /**
     * 接受用户发送的消息
     * @param message
     */
    void receiveMessage(message message);

}
