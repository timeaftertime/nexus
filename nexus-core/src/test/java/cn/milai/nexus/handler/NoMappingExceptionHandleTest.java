package cn.milai.nexus.handler;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import cn.milai.nexus.NexusConfig;

/**
 * 测试未捕获异常处理的测试类
 * @author milai
 * @date 2021.05.04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = { NoMappingExceptionHandleTest.TestConfiguration.class }, args = { ThrowExceptionController.ENABLE_ARG }
)
public class NoMappingExceptionHandleTest {

	@Configuration
	@Import(NexusConfig.class)
	static class TestConfiguration {
	}

	@Autowired
	private ThrowExceptionController throwExceptionController;

	@Test
	public void testNoMappingExceptionHandle() throws Exception {
		for (Class<? extends Exception> clazz : Arrays.array(
			IllegalArgumentException.class, RuntimeException.class, IllegalStateException.class
		)) {
			Assertions.assertThrows(clazz, () -> throwExceptionController.dispathException(clazz));
		}
	}

}
