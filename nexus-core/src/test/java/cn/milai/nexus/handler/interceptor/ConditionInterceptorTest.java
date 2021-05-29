package cn.milai.nexus.handler.interceptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cn.milai.nexus.handler.msg.BaseMsg;
import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * {@link ConditionInterceptor} 测试类
 * @author milai
 * @date 2021.05.29
 */
public class ConditionInterceptorTest {

	@Test
	public void testConditionInterceptor() {
		String id = Msg.generateId();
		Interceptor interceptor1 = new ConditionInterceptor(m -> m.getId().equals(id));
		Interceptor interceptor2 = new ConditionInterceptor(m -> m.getId().equals(id)) {
			protected boolean doPreHandle(ChannelHandlerContext ctx, Msg msg, Object handler) {
				return true;
			}
		};
		assertFalse(interceptor1.preHandle(null, specifyIdMsg(id), null));
		assertTrue(interceptor2.preHandle(null, specifyIdMsg(id), null));
		Msg msg = specifyIdMsg(id + "~");
		assertTrue(interceptor1.preHandle(null, msg, null));
		assertTrue(interceptor2.preHandle(null, msg, null));
	}

	@Test
	public void testMsgCodeInterceptors() {
		Set<Integer> codes = new HashSet<>(Arrays.asList(1, 2, 3));
		Interceptor allow = new AllowMsgCodeInterceptor(codes);
		Interceptor disallow = new DisallowMsgCodeInterceptor(codes);
		for (int i = 0; i < 10; i++) {
			BaseMsg msg = new BaseMsg(i);
			if (codes.contains(i)) {
				assertTrue(allow.preHandle(null, msg, disallow));
				assertFalse(disallow.preHandle(null, msg, disallow));
			} else {
				assertFalse(allow.preHandle(null, msg, disallow));
				assertTrue(disallow.preHandle(null, msg, disallow));
			}
		}
	}

	private Msg specifyIdMsg(String id) {
		return new BaseMsg(id, 0, 0, "{}");
	}

}
