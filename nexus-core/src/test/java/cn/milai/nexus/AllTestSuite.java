package cn.milai.nexus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.nexus.handler.HandlerTestSuite;
import cn.milai.nexus.handler.paramresolve.DefaultParamResolversTest;

@RunWith(Suite.class)
@SuiteClasses(
	{ HandlerTestSuite.class, DefaultParamResolversTest.class }
)
public class AllTestSuite {

}
