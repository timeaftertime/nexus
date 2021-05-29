package cn.milai.nexus.handler.interceptor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.nexus.handler.InterceptorTest;

@RunWith(Suite.class)
@SuiteClasses({ ConditionInterceptorTest.class, InterceptorTest.class })
public class InterceptorTestSuite {

}
