3
https://raw.githubusercontent.com/BanqiJane/Bilibili_Danmuji/master/Bilibilidanmuji/src/main/java/xyz/acproject/danmuji/entity/HostServerIp.java
package xyz.acproject.danmuji.entity;

import java.io.Serializable;

public class HostServerIp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5416269632134092211L;
	private String Host;
	private Integer port;
	public HostServerIp() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public HostServerIp(String host, Integer port) {
		super();
		Host = host;
		this.port = port;
	}
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "HostServerIp [Host=" + Host + ", port=" + port + "]";
	}
	
}
