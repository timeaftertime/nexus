package cn.milai.nexus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.milai.nexus.handler.HandlerTestSuite;

@RunWith(Suite.class)
@SuiteClasses(
	{ HandlerTestSuite.class }
)
public class AllTestSuite {

}
