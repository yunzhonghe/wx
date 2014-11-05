package com.dragon.spider.httpclient;

import java.util.Map;

import org.apache.http.HttpHost;

public class RequestModel {
	private HttpHost host;
	private String url;
	private Map<String, String> paras;
	private Map<String, String> headers;
	private String body;
	private String charset;
    
	public HttpHost getHost() {
		return host;
	} 

	public void setHost(String ip, int port) {
		this.host = new HttpHost(ip, port);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParas() {
		return paras;
	}

	public void setParas(Map<String, String> paras) {
		this.paras = paras;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHost(HttpHost host) {
		this.host = host;
	}

}
