package cn.milai.nexus.handler;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import cn.milai.nexus.NexusException;

/**
 * {@link ExceptionHandlerTable} 测试类
 * @author milai
 * @date 2021.05.26
 */
@RunWith(SpringRunner.class)
public class ExceptionHandlerTableTest {

	private final static MethodHandler PARENT = new MethodHandler(
		ExceptionHandlerTableTest.class, ExceptionHandlerTable.class.getMethods()[0]
	);

	private final static MethodHandler TEST_HANDLER = new MethodHandler(
		ExceptionHandlerTableTest.class, ExceptionHandlerTable.class.getMethods()[0]
	);

	@Test
	public void testRemappingException() {
		ExceptionHandlerTable table = new ExceptionHandlerTable();
		table.register(RuntimeException.class, TEST_HANDLER);
		assertThrows(
			ExceptionRemappingException.class, () -> table.register(RuntimeException.class, TEST_HANDLER)
		);
	}

	@Test
	public void testFindHandler() {
		ExceptionHandlerTable table = new ExceptionHandlerTable();
		table.register(RuntimeException.class, TEST_HANDLER);
		assertSame(TEST_HANDLER, table.findHandler(new RuntimeException()));
		assertSame(TEST_HANDLER, table.findHandler(new NexusException("")));
		assertSame(null, table.findHandler(new Exception()));
	}

	@Test
	public void testParentHandler() {
		ExceptionHandlerTable parent = new ExceptionHandlerTable();
		ExceptionHandlerTable child = new ExceptionHandlerTable(parent);
		parent.register(Exception.class, PARENT);
		child.register(RuntimeException.class, TEST_HANDLER);
		RuntimeException re = new RuntimeException();
		assertSame(PARENT, parent.findHandler(re));
		assertSame(TEST_HANDLER, child.findHandler(re));
		Exception e = new Exception();
		assertSame(PARENT, parent.findHandler(e));
		assertSame(PARENT, child.findHandler(e));
	}

}
