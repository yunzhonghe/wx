package com.dragon.spider.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.config.HttpClientApiConfig;

public class PublicWxManager {

	private static Map<String, HttpClientApiConfig> clients = new HashMap<String, HttpClientApiConfig>();

	public static HttpClientApiConfig getConfig(String name, String pwd) {
		String key = name + ":" + pwd;
		if (clients.get(key) != null) {
			return clients.get(key);
		} else {
			HttpClientApiConfig config = new HttpClientApiConfig();
			config.setName(name);
			config.setPassword(pwd);
			HttpClient client = new DefaultHttpClient();
			config.setClient(client);
			return config;
		}		
	}
}
