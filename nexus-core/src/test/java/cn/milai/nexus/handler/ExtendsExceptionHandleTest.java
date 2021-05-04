package cn.milai.nexus.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.milai.nexus.NexusConfig;

/**
 * 测试优先最佳匹配的异常处理测试类
 * @author milai
 * @date 2021.05.04
 */
@SpringBootTest(
	classes = { NexusConfig.class }, args = {
		ExtendsExceptionAdvice.ENABLE_ARG,
		ThrowExceptionController.ENABLE_ARG
	}
)
public class ExtendsExceptionHandleTest {

	@Autowired
	private ThrowExceptionController throwExceptionController;

	@Autowired
	private ExtendsExceptionAdvice extendsAdvice;

	@Test
	public void testExplicitCatch() throws Exception {
		int illegalNum = 3;
		int runtimeNum = 4;
		int exceptionNum = 2;
		assertExtendsAdvice(0, 0, 0);
		for (int i = 1; i <= illegalNum; i++) {
			throwExceptionController.dispathException(IllegalArgumentException.class);
			assertExtendsAdvice(i, 0, 0);
		}
		for (int i = 1; i <= runtimeNum; i++) {
			throwExceptionController.dispathException(RuntimeException.class);
			assertExtendsAdvice(illegalNum, i, 0);
		}
		for (int i = 1; i <= exceptionNum; i++) {
			throwExceptionController.dispathException(Exception.class);
			assertExtendsAdvice(illegalNum, runtimeNum, i);
		}
		assertExtendsAdvice(illegalNum, runtimeNum, exceptionNum);
	}

	private void assertExtendsAdvice(int argumentCount, int runtimeCount, int exceptionCount) {
		assertEquals(argumentCount, extendsAdvice.getArgumentCount());
		assertEquals(runtimeCount, extendsAdvice.getRuntimeCount());
		assertEquals(exceptionCount, extendsAdvice.getExceptionCount());
	}

}
