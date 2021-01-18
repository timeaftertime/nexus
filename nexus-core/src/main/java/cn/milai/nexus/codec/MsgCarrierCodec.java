package cn.milai.nexus.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.nexus.Protobufs.MsgCarrier;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

/**
 * Netty Protobuf 编解码器
 * @author milai
 * @date 2020.12.30
 */
public class MsgCarrierCodec extends ByteToMessageCodec<MsgCarrier> {

	private static final Logger LOG = LoggerFactory.getLogger(MsgCarrierCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgCarrier msg, ByteBuf out) throws Exception {
		LOG.debug("开始编码 MsgCarrier 消息, ctx = {}, msg = {}", ctx, msg);
		byte[] data = msg.toByteArray();
		out.writeInt(data.length);
		out.writeBytes(data);
		ctx.flush();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int len = in.readInt();
		if (in.readableBytes() < len) {
			in.resetReaderIndex();
			return;
		}
		MsgCarrier msg = MsgCarrier.parseFrom(Unpooled.copiedBuffer(in.readBytes(len)).array());
		out.add(msg);
		LOG.debug("成功解码 MsgCarrier 消息, ctx = {}, msg = {}", ctx, msg);
	}

}
