package cn.milai.nexus.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.milai.nexus.Protobufs.MsgCarrier;
import cn.milai.nexus.handler.msg.BaseMsg;
import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * {@link Msg} 与 {@link MsgCarrier} 编解码器
 * @author milai
 * @date 2021.01.18
 */
public class MsgToCarrierCodec extends MessageToMessageCodec<MsgCarrier, Msg> {

	private static final Logger LOG = LoggerFactory.getLogger(MsgToCarrierCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, List<Object> out) throws Exception {
		LOG.debug("开始编码 Msg 消息, ctx = {}, msg = {}", ctx, msg);
		out.add(
			MsgCarrier.newBuilder()
				.setId(msg.getId())
				.setCode(msg.getCode())
				.setTime(msg.getTime())
				.setData(msg.getData().toString())
				.build()
		);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, MsgCarrier msg, List<Object> out) throws Exception {
		Msg m = new BaseMsg(msg.getId(), msg.getCode(), msg.getData());
		out.add(m);
		LOG.debug("完成解码 Msg 消息, ctx = {}, msg = {}", ctx, m);
	}

}
