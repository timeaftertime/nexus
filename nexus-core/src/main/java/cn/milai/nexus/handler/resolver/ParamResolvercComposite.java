package cn.milai.nexus.handler.resolver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import cn.milai.nexus.handler.Msg;
import io.netty.channel.ChannelHandlerContext;

/**
 * {@link ParamResolver} 的组合模式实现
 * @author milai
 * @date 2021.01.02
 */
@Component
public class ParamResolvercComposite implements ParamResolver, InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(ParamResolvercComposite.class);

	private List<ParamResolver> resolver = new ArrayList<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		resolver.add(new SingleTypeParamResolver());
		resolver.add(new ModelAttrParamResolver());
		resolver.add(new ChannelHandlerContextParamResolver());
	}

	@Override
	public boolean supports(MethodParameter param) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object resolve(MethodParameter param, ChannelHandlerContext ctx, Msg msg) {
		String paramName = ParamResolver.paramSignature(param);
		for (ParamResolver r : resolver) {
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

}
