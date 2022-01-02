package cn.milai.nexus.handler.paramresolve;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * 反射创建对应类型并通过 getter 、setter 方法来设置属性的 {@link ParamResolver} 实现
 * @author milai
 * @date 2021.01.02
 */
@Order(2)
@Component
public class ModelAttrParamResolver extends AbstractParamResolver {

	private static final Logger LOG = LoggerFactory.getLogger(ModelAttrParamResolver.class);

	@Override
	public boolean supports(MethodParameter param) {
		try {
			return param.getParameterType().getConstructor() != null;
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

	@Override
	public Object doResolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg)
		throws UnresolveableParamException {
		try {
			Object bean = param.getParameterType().newInstance();
			for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(param.getParameterType())) {
				Method writeMethod = pd.getWriteMethod();
				if (writeMethod == null) {
					continue;
				}
				writeMethod.invoke(bean, msg.getData().as(pd.getName(), pd.getPropertyType()));
			}
			return bean;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
			LOG.warn("解析 data 为 {} 失败， e = {}", param.getParameterType().getName(), ExceptionUtils.getStackFrames(e));
			throw new UnresolveableParamException(ParamResolver.paramSignature(param));
		}
	}

}
