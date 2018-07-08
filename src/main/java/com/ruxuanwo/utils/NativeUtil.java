package com.ruxuanwo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取机器信息
 *
 * @author 如漩涡
 *
 */
public final class NativeUtil {

	private NativeUtil() {
	}

	/** 获取机器名 */
	public static final String getHostName() {
		String hostName = "";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			new RuntimeException("获取机器名称失败", e);
		}
		return hostName;
	}

	/** 获取网卡序列号 */
	public static final String getDUID() {
		String address = "";
		String command = "cmd.exe /c ipconfig /all";
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("DUID") > 0) {
					int index = line.indexOf(":");
					index += 2;
					address = line.substring(index);
					break;
				}
			}
			br.close();
		} catch (IOException e) {
		}
		return address;
	}

	public static void main(String[] args) {
		System.out.println(getHostName());
	}
}
