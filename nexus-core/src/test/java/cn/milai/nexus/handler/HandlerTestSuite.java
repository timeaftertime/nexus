package cn.milai.nexus.handler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.nexus.handler.interceptor.InterceptorTestSuite;
import cn.milai.nexus.handler.paramresolve.DefaultParamResolversTest;

@RunWith(Suite.class)
@SuiteClasses(
	{ ExceptionHandlerTableTest.class, ExtendsExceptionHandleTest.class, NoMappingExceptionHandleTest.class,
		InterceptorTestSuite.class, DefaultParamResolversTest.class }
)
public class HandlerTestSuite {

}
