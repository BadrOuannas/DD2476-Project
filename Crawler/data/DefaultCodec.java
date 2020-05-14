23
https://raw.githubusercontent.com/mrchengwenlong/NettyIM/master/im_lib/src/main/java/com/takiku/im_lib/codec/DefaultCodec.java
package com.takiku.im_lib.codec;

import com.google.protobuf.MessageLiteOrBuilder;
import com.takiku.im_lib.codec.Codec;
import com.takiku.im_lib.protobuf.PackProtobuf;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class DefaultCodec implements Codec {
    @Override
    public MessageToMessageEncoder<MessageLiteOrBuilder> EnCoder() {
        return new ProtobufEncoder();
    }

    @Override
    public MessageToMessageDecoder<ByteBuf> DeCoder() {
        return new ProtobufDecoder(PackProtobuf.Pack.getDefaultInstance());
    }
}
