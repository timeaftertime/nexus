package cn.milai.nexus.handler.paramresolve;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import cn.milai.nexus.handler.msg.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * {@link ParamResolver} 的组合模式实现
 * @author milai
 * @date 2021.01.02
 */
public class ParamResolverComposite implements ParamResolver {

	private static final Logger LOG = LoggerFactory.getLogger(ParamResolverComposite.class);

	private List<ParamResolver> resolvers;

	public ParamResolverComposite(Collection<ParamResolver> resolvers) {
		this.resolvers = new ArrayList<>(resolvers);
		AnnotationAwareOrderComparator.sort(this.resolvers);
	}

	public void addParamResolver(ParamResolver resolver) {
		this.resolvers.add(resolver);
		AnnotationAwareOrderComparator.sort(this.resolvers);
	}

	@Override
	public boolean supports(MethodParameter param) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object resolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg) {
		String paramName = ParamResolver.paramSignature(param);
		for (ParamResolver r : resolvers) {
			if (!r.supports(param)) {
				LOG.debug("不支持的参数类型: param = {}, solver = {}", paramName, r);
				continue;
			}
			try {
				return r.resolve(param, ctx, msg);
			} catch (UnresolveableParamException e) {
				LOG.debug("尝试解析参数失败: param = {}, e = {}", paramName, ExceptionUtils.getStackTrace(e));
			}
		}
		throw new UnresolveableParamException(paramName);
	}

	/**
	 * 解析指定方法的参数，返回解析结果
	 * @param paramResolver
	 * @param method
	 * @param ctx
	 * @param msg
	 * @return
	 */
	public Object[] resolveParams(Method method, ChannelHandlerContext ctx, Msg msg) {
		Object[] params = new Object[method.getParameterCount()];
		for (int i = 0; i < params.length; i++) {
			params[i] = resolve(new MethodParameter(method, i), ctx, msg);
		}
		return params;
	}

}
