package com.dragon.spider.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	/**
	 * 连接超时时间
	 */
	public static final int CONNECTION_TIMEOUT_MS = 10000;

	/**
	 * 读取数据超时时间
	 */
	public static final int SO_TIMEOUT_MS = 10000;

	public static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=utf-8";

	public static final String CONTENT_TYPE_XML_CHARSET = "application/xml;charset=utf-8";

	/**
	 * httpclient读取内容时使用的字符集
	 */
	public static final String CONTENT_CHARSET_GBK = "GBK";

	public static final String CONTENT_CHARSET_UTF8 = "UTF-8";

	public static final Charset UTF_8 = Charset.forName(CONTENT_CHARSET_UTF8);

	public static final Charset GBK = Charset.forName(CONTENT_CHARSET_GBK);

	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String simpleGetInvoke(String url, Map<String, String> params, HttpHost host) throws ClientProtocolException,
			IOException, URISyntaxException {
		return simpleGetInvoke(url, params, host, CONTENT_CHARSET_UTF8);
	}

	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String simpleGetInvoke(String url, Map<String, String> params, HttpHost host, String charset)
			throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient client = buildHttpClient(false, host);
		HttpGet get = buildHttpGet(url, params);
		HttpResponse response = client.execute(get);
		assertStatus(response);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, charset);
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String simpleGetInvoke(RequestModel request) throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient client = buildHttpClient(false, request.getHost());
		HttpGet get = buildHttpGet(request.getUrl(), request.getParas());
		HttpResponse response = client.execute(get);
		assertStatus(response);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单get调用并且保持之前的client
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String simpleGetInvoke(HttpClient client, RequestModel request) throws ClientProtocolException, IOException,
			URISyntaxException {
		HttpGet get = buildHttpGet(request.getUrl(), request.getParas());
		HttpResponse response = client.execute(get);
		assertStatus(response);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单post调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	/*
	 * public static String simplePostInvoke(String url, Map<String, String>
	 * params,Map<String, String> inHeaders, HttpHost host) throws
	 * URISyntaxException, ClientProtocolException, IOException { return
	 * simplePostInvoke(url, params,inHeaders,host, CONTENT_CHARSET_UTF8); }
	 */

	/**
	 * 简单post调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String simplePostInvoke(RequestModel request) throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient client = buildHttpClient(false, request.getHost());
		HttpPost postMethod = buildHttpPost(request.getUrl(), request.getParas());
		if (null != request.getHeaders()) {
			Iterator<String> headIt = request.getHeaders().keySet().iterator();

			while (headIt.hasNext()) {
				String key = headIt.next();
				String value = request.getHeaders().get(key);
				postMethod.addHeader(key, value);
			}
		}

		HttpResponse response = client.execute(postMethod);
		assertStatus(response);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单post调用并保持原来的client连接
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String simplePostInvoke(RequestModel request, HttpClient client) throws URISyntaxException, ClientProtocolException,
			IOException {
		HttpPost postMethod = buildHttpPost(request.getUrl(), request.getParas());

		if (null != request.getHeaders()) {
			Iterator<String> headIt = request.getHeaders().keySet().iterator();

			while (headIt.hasNext()) {
				String key = headIt.next();
				String value = request.getHeaders().get(key);
				postMethod.addHeader(key, value);
			}
		}

		HttpResponse response = client.execute(postMethod);
		assertStatus(response);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
			return returnStr;
		}
		return null;
	}

	/**
	 * 创建HttpClient
	 * 
	 * @param isMultiThread
	 * @return
	 */
	public static HttpClient buildHttpClient(boolean isMultiThread, HttpHost host) {
		CloseableHttpClient client;
		if (null != host) {
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);
			if (isMultiThread)
				client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager())
						.setRoutePlanner(routePlanner).build();
			else
				client = HttpClientBuilder.create().setRoutePlanner(routePlanner).build();
		} else {
			if (isMultiThread)
				client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager()).build();
			else
				client = HttpClientBuilder.create().build();
		}
		return client;
	}

	/**
	 * 构建httpPost对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 */
	public static HttpPost buildHttpPost(String url, Map<String, String> params) throws UnsupportedEncodingException, URISyntaxException {
		HttpPost post = new HttpPost(url);
		setCommonHttpMethod(post);
		HttpEntity he = null;
		if (params != null) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			he = new UrlEncodedFormEntity(formparams, UTF_8);
			post.setEntity(he);
		}
		return post;

	}

	/**
	 * 构建httpGet对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpGet buildHttpGet(String url, Map<String, String> params) throws URISyntaxException {
		HttpGet get = new HttpGet(buildGetUrl(url, params));
		return get;
	}

	/**
	 * build getUrl str
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String buildGetUrl(String url, Map<String, String> params) {
		StringBuffer uriStr = new StringBuffer(url);
		if (params != null) {
			List<NameValuePair> ps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				ps.add(new BasicNameValuePair(key, params.get(key)));
			}
			uriStr.append("?");
			uriStr.append(URLEncodedUtils.format(ps, UTF_8));
		}
		return uriStr.toString();
	}

	/**
	 * 设置HttpMethod通用配置
	 * 
	 * @param httpMethod
	 */
	public static void setCommonHttpMethod(HttpRequestBase httpMethod) {
		httpMethod.setHeader(HTTP.CONTENT_ENCODING, CONTENT_CHARSET_UTF8);// setti
	}

	/**
	 * 设置成消息体的长度 setting MessageBody length
	 * 
	 * @param httpMethod
	 * @param he
	 */
	public static void setContentLength(HttpRequestBase httpMethod, HttpEntity he) {
		if (he == null) {
			return;
		}
		httpMethod.setHeader(HTTP.CONTENT_LEN, String.valueOf(he.getContentLength()));
	}

	/**
	 * 构建公用RequestConfig
	 * 
	 * @return
	 */
	public static RequestConfig buildRequestConfig() {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT_MS).setConnectTimeout(CONNECTION_TIMEOUT_MS)
				.build();
		return requestConfig;
	}

	/**
	 * 强验证必须是200状态否则报异常
	 * 
	 * @param res
	 * @throws HttpException
	 */
	static void assertStatus(HttpResponse res) throws IOException {
		switch (res.getStatusLine().getStatusCode()) {
		case 200:
			break;
		default:
			throw new IOException("服务器响应状态异常,失败.");
		}
	}

	private HttpClientUtils() {
	}

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		HttpHost proxy = new HttpHost("117.59.217.228", 80);
		System.out.println(simpleGetInvoke("http://www.baidu.com", new HashMap<String, String>(), proxy));
	}
}
