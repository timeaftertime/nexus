package cn.milai.nexus.handler.paramresolve;

import org.springframework.core.MethodParameter;

/**
 * 专门解析某类型的 {@link ParamResolver}
 * @author milai
 * @date 2021.05.29
 */
public abstract class TypeParamResolver extends AbstractParamResolver {

	private Class<?> clazz;

	public TypeParamResolver(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean supports(MethodParameter param) {
		return clazz.isAssignableFrom(param.getParameterType());
	}

}
