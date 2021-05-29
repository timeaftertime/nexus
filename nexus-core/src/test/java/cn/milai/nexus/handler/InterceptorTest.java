package cn.milai.nexus.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import cn.milai.nexus.NexusConfig;
import cn.milai.nexus.NexusException;
import cn.milai.nexus.handler.interceptor.InterceptAllInterceptor;

/**
 * 测试拦截器
 * @author milai
 * @date 2021.05.29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = InterceptorTest.TestConfiguration.class,
	args = { ThrowExceptionController.ENABLE_ARG, InterceptAllInterceptor.ENABLE_ARG }
)
public class InterceptorTest {

	@Configuration
	@Import(NexusConfig.class)
	static class TestConfiguration {
	}

	@Autowired
	private ThrowExceptionController controller;

	@Test
	public void testInterceptorAll() throws Exception {
		controller.dispathException(NexusException.class);
		controller.dispathException(RuntimeException.class);
		controller.dispathException(Exception.class);
	}

}
