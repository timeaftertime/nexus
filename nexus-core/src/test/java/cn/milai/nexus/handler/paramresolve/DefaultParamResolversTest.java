package cn.milai.nexus.handler.paramresolve;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.milai.nexus.NexusConfig;
import cn.milai.nexus.handler.msg.Msg;
import cn.milai.nexus.handler.msg.SingleMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 默认 {@link ParamResolver} 的测试类
 * @author milai
 * @date 2021.05.04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NexusConfig.class })
public class DefaultParamResolversTest {

	@Autowired
	public List<ParamResolver> paramResolvers;

	@Test
	public void testResolve() throws NoSuchMethodException, SecurityException {
		ParamResolverComposite paramResolver = new ParamResolverComposite(paramResolvers);
		ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
		int age = 10;
		Msg msg = new SingleMsg(0, "age", age);
		assertArrayEquals(new Object[] { ctx }, paramResolver.resolveParams(contextMethod(), ctx, msg));
		assertArrayEquals(new Object[] { age }, paramResolver.resolveParams(intMethod(), ctx, msg));
		Object[] params = paramResolver.resolveParams(modelMethod(), ctx, msg);
		assertEquals(1, params.length);
		Model model = (Model) params[0];
		assertEquals(age, model.age);
		assertNull(model.name);
		assertArrayEquals(new Object[] { msg }, paramResolver.resolveParams(msgMethod(), ctx, msg));
	}

	public static class Model {
		private int age;
		private String name;

		public int getAge() { return age; }

		public void setAge(int age) { this.age = age; }

		public String getName() { return name; }

		public void setName(String name) { this.name = name; }
	}

	private Method contextMethod() throws NoSuchMethodException, SecurityException {
		return DefaultParamResolversTest.class.getDeclaredMethod(
			"needContext", ChannelHandlerContext.class
		);
	}

	private Method intMethod() throws NoSuchMethodException, SecurityException {
		return DefaultParamResolversTest.class.getDeclaredMethod(
			"needInt", int.class
		);
	}

	private Method modelMethod() throws NoSuchMethodException, SecurityException {
		return DefaultParamResolversTest.class.getDeclaredMethod(
			"needModel", Model.class
		);
	}

	private Method msgMethod() throws NoSuchMethodException, SecurityException {
		return DefaultParamResolversTest.class.getDeclaredMethod(
			"needMsg", Msg.class
		);
	}

	void needContext(ChannelHandlerContext ctx) {}

	void needInt(int age) {}

	void needModel(Model model) {}

	void needMsg(Msg msg) {}

}
