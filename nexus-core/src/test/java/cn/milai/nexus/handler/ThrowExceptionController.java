package cn.milai.nexus.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import cn.milai.nexus.annotation.MsgController;
import cn.milai.nexus.annotation.MsgMapping;
import cn.milai.nexus.handler.msg.SingleMsg;

/**
 * 用于测试异常处理的配置类
 * @author milai
 * @date 2021.05.04
 */
@MsgController
@Configuration
@ConditionalOnProperty(ThrowExceptionController.ENABLE_PROPERTY)
public class ThrowExceptionController {

	public static final int THROW_MSG_CODE = 1;

	public static final String PARAM_EXCEPTION_NAME = "exception";

	public static final String ENABLE_PROPERTY = "cn.milai.nexus.handler.throw-exception-controller.enable";

	public static final String ENABLE_ARG = "--" + ENABLE_PROPERTY;

	@Autowired
	private MsgDispatcher msgDispatcher;

	@MsgMapping(THROW_MSG_CODE)
	public void throwException(String exception) throws Exception {
		throw (Exception) Class.forName(exception).newInstance();
	}

	/**
	 * 向当前 {@link ApplicationContext} 中的 {@link MsgDispatcher} 分发一个要求抛出指定异常的消息
	 * @param clazz
	 * @throws Exception
	 */
	public void dispathException(Class<? extends Exception> clazz) throws Exception {
		msgDispatcher.dispatch(
			null, new SingleMsg(THROW_MSG_CODE, PARAM_EXCEPTION_NAME, clazz.getName())
		);
	}

}
