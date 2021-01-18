package cn.milai.nexus;

/**
 * Nexus 顶级异常
 * @author milai
 * @date 2021.01.14
 */
public class NexusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NexusException(Throwable e) {
		super(e);
	}

	public NexusException(String format, Object... args) {
		super(String.format(format, args));
	}

}
