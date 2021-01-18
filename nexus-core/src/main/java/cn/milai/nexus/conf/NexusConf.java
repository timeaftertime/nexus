package cn.milai.nexus.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Nexus 的 Netty 相关配置
 * @author milai
 * @date 2021.01.14
 */
@Component
@ConfigurationProperties(NexusConf.PREFIX)
public class NexusConf {

	public static final String PREFIX = "nexus";

	/**
	 * 客户端模式
	 */
	public static final String MODE_CLIENT = "client";

	/**
	 * 服务端模式
	 */
	public static final String MODE_SERVER = "server";

	/**
	 * 客户端模式时，需要连接的服务端端口号
	 * 服务端时，需要绑定的端口号
	 */
	private int port = 8000;

	/**
	 * 客户端模式时需要连接的服务端主机名
	 */
	private String host = "localhost";

	/**
	 * Netty Socket 启动模式，server 或 client
	 */
	private String mode;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMode() {
		return mode;
	}

	/**
	 * 判断当前配置是否为客户端
	 * @return
	 */
	public boolean isClientMode() {
		return StringUtils.equalsIgnoreCase(mode, MODE_CLIENT);
	}

	/**
	 * 判断当前配置是否为服务端
	 * @return
	 */
	public boolean isServerMode() {
		return StringUtils.equalsIgnoreCase(mode, MODE_SERVER);
	}

	public void setMode(String mode) {
		if (!StringUtils.equalsAnyIgnoreCase(mode, MODE_CLIENT) && !StringUtils.equalsIgnoreCase(mode, MODE_SERVER)) {
			throw new IllegalArgumentException(
				String.format("%s.mode 只能为 %s 或 %s: %s", PREFIX, MODE_CLIENT, MODE_SERVER, mode)
			);
		}
		this.mode = mode;
	}

}
