package com.dragon.spider.api;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetTokenResponse;
import com.dragon.spider.httpclient.RequestModel;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.NetWorkCenter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * API基类，提供一些通用方法
 * 包含自动刷新token、通用get post请求等
 * @author peiyu
 */
public abstract class HttpClientBaseAPI {
    protected static final String BASE_API_URL = "https://api.weixin.qq.com/";

    protected static final String SYS_BUSY = "-1";

    protected static final String SUCCESS = "0";

    protected static final String TOKEN_ERROR = "40001";

    protected static final String TOKEN_TYPE_ERROR = "40002";

    protected static final String TOKEN_TIMEOUT = "42001";

    protected final HttpClientApiConfig config;

    //用于刷新token时锁住config，防止多次刷新
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public HttpClientBaseAPI(HttpClientApiConfig config) {
        this.config = config;
    }

    public static HttpClient client = new DefaultHttpClient();	
    
    /**
     * 
     */
    protected void refreshToken() {
        writeLock.lock();
        try {
            config.refreshing = true;
            try {
				String token  = getTokenByLogins(config.getName(),config.getPassword());
				if(token ==null){
					return;
				}else{
					 HttpClientBaseAPI.this.config.setToken(token);
				}
			} catch (Exception e) {			
				e.printStackTrace();
			}
        } finally {
            config.refreshing = false;
            writeLock.unlock();
        }
    }
    
    /**MD5加密
	 * @param 加密前密码
	 * @return 加密后密码
	 * */
	private String MD5Encode(String password) {
		MessageDigest md5 = null;
		byte[] bs = password.getBytes();
		StringBuffer buffer = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(bs);
			bs = md5.digest();
			for (int i = 0; i < bs.length; i++) {

				if ((bs[i] & 0xff) < 0x10) {
					buffer.append("0");
				}
				buffer.append(Long.toString(bs[i] & 0xff, 16));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 获取Token
	 * @param reposne返回的数据
	 * @return Token
	 * */
	private String getToken(String response){
		System.out.println(response);
		String [] bs=response.split("&token=")[1].split("','");	
		String token=bs[0].split(",")[0];
		token=token.substring(0, token.length()-2);
		return token;
	}
	
	/**
	 * 请求登入
	 * @param 用户名,加密后密码,验证码
	 * @return  reposne返回的数据,包含Token的String字段
	 * */
	private String getTokenByLogins(String name,String pwd)throws Exception{
		String resultCont="";
		
		// 请求登录
		HttpPost post = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
		post.addHeader("Referer", "https://mp.weixin.qq.com/");
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("f", "json"));
		nvp.add(new BasicNameValuePair("pwd", MD5Encode(pwd)));
		nvp.add(new BasicNameValuePair("username", name));
		post.setEntity(new UrlEncodedFormEntity(nvp));
		resultCont=EntityUtils.toString(client.execute(post).getEntity());	
		String token = getToken(resultCont);
		/*
		
			
		post = new HttpPost("https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&token="+token+"&lang=zh_CN");
		post.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="+token+"&lang=zh_CN");
		nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("f", "json"));
		nvp.add(new BasicNameValuePair("pwd", MD5Encode(pwd)));
		nvp.add(new BasicNameValuePair("username", name));
		post.setEntity(new UrlEncodedFormEntity(nvp));
		resultCont=EntityUtils.toString(client.execute(post).getEntity());	
		System.out.println("list"+ resultCont);		
		
		post = new HttpPost("https://mp.weixin.qq.com/cgi-bin/getcontactinfo?t=ajax-getcontactinfo&lang=zh_CN&fakeid="+token);
		post.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="+token+"&lang=zh_CN");
		nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("f", "json"));
		nvp.add(new BasicNameValuePair("pwd", MD5Encode(pwd)));
		nvp.add(new BasicNameValuePair("username", name));
		post.setEntity(new UrlEncodedFormEntity(nvp));
		resultCont=EntityUtils.toString(client.execute(post).getEntity());	*/
		
		System.out.println(resultCont);		
		return token;
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
	public static String simplePostInvoke(RequestModel request) throws URISyntaxException, ClientProtocolException, IOException {
		/*HttpClient client = buildHttpClient(false, request.getHost());*/
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
		//System.out.print(client.);
		/*assertStatus(response);*/
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
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
		//HttpClient client = buildHttpClient(false, request.getHost());
		HttpGet get = buildHttpGet(request.getUrl(), request.getParas());
		HttpResponse response = client.execute(get);	
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, request.getCharset());
			return returnStr;
		}
		return null;
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
			uriStr.append(URLEncodedUtils.format(ps, "utf-8"));
		}
		return uriStr.toString();
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
			he = new UrlEncodedFormEntity(formparams, "utf-8");
			post.setEntity(he);
		}
		return post;

	}

	/**
	 * 设置HttpMethod通用配置
	 * 
	 * @param httpMethod
	 */
	public static void setCommonHttpMethod(HttpRequestBase httpMethod) {
		httpMethod.setHeader(HTTP.CONTENT_ENCODING, "utf-8");// setti
	}

}
