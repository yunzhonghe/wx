package com.dragon.spider.api.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Weixin {
	private final static Log log = LogFactory.getLog(Weixin.class);
	public final static String HOST = "https://mp.weixin.qq.com";
	public final static String LOGIN_URL = "https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
	public final static String INDEX_URL = "https://mp.weixin.qq.com/cgi-bin/indexpage?t=wxm-index&lang=zh_CN";
	public final static String FANS_URL = "https://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0";
	public final static String LOGOUT_URL = "https://mp.weixin.qq.com/cgi-bin/logout?t=wxm-logout&lang=zh_CN";
	public final static String DOWNLOAD_URL = "https://mp.weixin.qq.com/cgi-bin/downloadfile?";
	public final static String VERIFY_CODE = "https://mp.weixin.qq.com/cgi-bin/verifycode?";
	public final static String POST_MSG = "https://mp.weixin.qq.com/cgi-bin/masssend?t=ajax-response";
	public final static String VIEW_HEAD_IMG = "https://mp.weixin.qq.com/cgi-bin/viewheadimg";
	public final static String GET_IMG_DATA = "https://mp.weixin.qq.com/cgi-bin/getimgdata";
	public final static String GET_REGIONS = "https://mp.weixin.qq.com/cgi-bin/getregions";
	public final static String GET_MESSAGE = "https://mp.weixin.qq.com/cgi-bin/getmessage";
	public final static String OPER_ADVANCED_FUNC = "https://mp.weixin.qq.com/cgi-bin/operadvancedfunc";
	public final static String MASSSEND_PAGE = "https://mp.weixin.qq.com/cgi-bin/masssendpage";
	public final static String FILE_MANAGE_PAGE = "https://mp.weixin.qq.com/cgi-bin/filemanagepage";
	public final static String OPERATE_APPMSG = "https://mp.weixin.qq.com/cgi-bin/operate_appmsg";
	public final static String FMS_TRANSPORT = "https://mp.weixin.qq.com/cgi-bin/fmstransport";
	public final static String CONTACT_MANAGE_PAGE = "https://mp.weixin.qq.com/cgi-bin/contactmanagepage";
	public final static String OPER_SELF_MENU = "https://mp.weixin.qq.com/cgi-bin/operselfmenu";
	public final static String REPLY_RULE_PAGE = "https://mp.weixin.qq.com/cgi-bin/replyrulepage";
	public final static String SINGLE_MSG_PAGE = "https://mp.weixin.qq.com/cgi-bin/singlemsgpage";
	public final static String USER_INFO_PAGE = "https://mp.weixin.qq.com/cgi-bin/userinfopage";
	public final static String DEV_APPLY = "https://mp.weixin.qq.com/cgi-bin/devapply";
	public final static String UPLOAD_MATERIAL = "https://mp.weixin.qq.com/cgi-bin/uploadmaterial?cgi=uploadmaterial&type=2&token=416919388&t=iframe-uploadfile&lang=zh_CN&formId=1";

	public final static String USER_AGENT_H = "User-Agent";
	public final static String REFERER_H = "Referer";
	public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22";
	public final static String UTF_8 = "UTF-8";

	private HttpClient client = new HttpClient();

	private Cookie[] cookies;
	private String cookiestr;

	private String token;
	private int loginErrCode;
	private String loginErrMsg;
	private int msgSendCode;
	private String msgSendMsg;

	private String loginUser;
	private String loginPwd;
	public boolean isLogin = false;

	public Weixin(String user, String pwd) {
		this.loginUser = user;
		this.loginPwd = pwd;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}

	public String getCookiestr() {
		return cookiestr;
	}

	public void setCookiestr(String cookiestr) {
		this.cookiestr = cookiestr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getLoginErrCode() {
		return loginErrCode;
	}

	public void setLoginErrCode(int loginErrCode) {
		this.loginErrCode = loginErrCode;
	}

	public String getLoginErrMsg() {
		return loginErrMsg;
	}

	public void setLoginErrMsg(String loginErrMsg) {
		this.loginErrMsg = loginErrMsg;
	}

	public int getMsgSendCode() {
		return msgSendCode;
	}

	public void setMsgSendCode(int msgSendCode) {
		this.msgSendCode = msgSendCode;
	}

	public String getMsgSendMsg() {
		return msgSendMsg;
	}

	public void setMsgSendMsg(String msgSendMsg) {
		this.msgSendMsg = msgSendMsg;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * 登录,登录失败会重复请求登录
	 */
	public void login() {
		boolean bool = _login();
		while (!bool) {
			String info = "【登录失败】【错误代码：" + this.loginErrMsg + "】【账号：" + this.loginUser + "】正在尝试重新登录....";
			log.debug(info);
			System.out.println(info);
			bool = _login();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				bool = _login();
			}

		}
		System.out.println("登陆成功：");
	}

	/**
	 * 发送登录信息,记录cookie，登录状态，token等信息
	 * 
	 * @return
	 */
	private boolean _login() {
		try {
			PostMethod post = new PostMethod(LOGIN_URL);
			post.setRequestHeader(USER_AGENT_H, USER_AGENT);
			NameValuePair[] params = new NameValuePair[] { new NameValuePair("username", this.loginUser),
					new NameValuePair("pwd", MD5Encode(this.loginPwd)), new NameValuePair("f", "json"), new NameValuePair("imagecode", ""), };
			post.setRequestHeader("Referer", "https://mp.weixin.qq.com/");
			post.setQueryString(params);
			int status = client.executeMethod(post);
			if (status == HttpStatus.SC_OK) {
				String ret = post.getResponseBodyAsString();
				LoginJson retcode = JSON.parseObject(ret, LoginJson.class);
				if (retcode.getRet() == 0 && retcode.getErrCode() == 0) {
					this.cookies = client.getState().getCookies();
					StringBuffer cookie = new StringBuffer();
					for (Cookie c : client.getState().getCookies()) {
						cookie.append(c.getName()).append("=").append(c.getValue()).append(";");
					}
					this.cookiestr = cookie.toString();
					this.isLogin = true;
					this.token = getToken(retcode.getErrMsg());
					return true;
				}
				int errCode = retcode.getErrCode();
				this.loginErrCode = errCode;
				switch (errCode) {

				case -1:
					this.loginErrMsg = "系统错误";
					return false;
				case -2:
					this.loginErrMsg = "帐号或密码错误";
					return false;
				case -3:
					this.loginErrMsg = "密码错误";
					return false;
				case -4:
					this.loginErrMsg = "不存在该帐户";
					return false;
				case -5:
					this.loginErrMsg = "访问受限";
					return false;
				case -6:
					this.loginErrMsg = "需要输入验证码";
					return false;
				case -7:
					this.loginErrMsg = "此帐号已绑定私人微信号，不可用于公众平台登录";
					return false;
				case -8:
					this.loginErrMsg = "邮箱已存在";
					return false;
				case -32:
					this.loginErrMsg = "验证码输入错误";
					return false;
				case -200:
					this.loginErrMsg = "因频繁提交虚假资料，该帐号被拒绝登录";
					return false;
				case -94:
					this.loginErrMsg = "请使用邮箱登陆";
					return false;
				case 10:
					this.loginErrMsg = "该公众会议号已经过期，无法再登录使用";
					return false;
				case 65201:
				case 65202:
					this.loginErrMsg = "成功登陆，正在跳转...";
					return true;
				case 0:
					this.loginErrMsg = "成功登陆，正在跳转...";
					return true;
				default:
					this.loginErrMsg = "未知的返回";
					return false;
				}
			}
		} catch (Exception e) {
			String info = "【登录失败】【发生异常：" + e.getMessage() + "】";
			System.err.println(info);
			log.debug(info);
			log.info(info);
			return false;
		}
		return false;
	}

	/**
	 * 从登录成功的信息中分离出token信息
	 * 
	 * @param s
	 * @return
	 */
	private String getToken(String s) {
		try {
			if (StringUtils.isBlank(s))
				return null;
			String[] ss = StringUtils.split(s, "?");
			String[] params = null;
			if (ss.length == 2) {
				if (!StringUtils.isBlank(ss[1]))
					params = StringUtils.split(ss[1], "&");
			} else if (ss.length == 1) {
				if (!StringUtils.isBlank(ss[0]) && ss[0].indexOf("&") != -1)
					params = StringUtils.split(ss[0], "&");
			} else {
				return null;
			}
			for (String param : params) {
				if (StringUtils.isBlank(param))
					continue;
				String[] p = StringUtils.split(param, "=");
				if (null != p && p.length == 2 && StringUtils.equalsIgnoreCase(p[0], "token"))
					return p[1];

			}
		} catch (Exception e) {
			String info = "【解析Token失败】【发生异常：" + e.getMessage() + "】";
			System.err.println(info);
			log.debug(info);
			log.info(info);
			return null;
		}
		return null;
	}

	/**
	 * 获取首页
	 * 
	 * @throws org.apache.commons.httpclient.HttpException
	 * 
	 * @throws java.io.IOException
	 */
	public void index() throws HttpException, IOException {
		GetMethod get = new GetMethod(INDEX_URL);
		get.setRequestHeader(USER_AGENT_H, USER_AGENT);
		get.setRequestHeader("Cookie", this.cookiestr);
		int status = client.executeMethod(get);
		if (status == HttpStatus.SC_OK) {
			System.out.println(get.getResponseBodyAsString());
		}
	}

	/**
	 * 登出操作
	 * 
	 * @throws org.apache.commons.httpclient.HttpException
	 * 
	 * @throws java.io.IOException
	 */
	public void logout() throws HttpException, IOException {
		GetMethod get = new GetMethod(LOGOUT_URL);
		get.setRequestHeader(USER_AGENT_H, USER_AGENT);
		get.setRequestHeader("Cookie", this.cookiestr);
		int status = client.executeMethod(get);
		if (status == HttpStatus.SC_OK) {
			System.err.println("-----------注销登录成功-----------");
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @throws org.apache.commons.httpclient.HttpException
	 * 
	 * @throws java.io.IOException
	 */
	public InputStream code() throws HttpException, IOException {
		GetMethod get = new GetMethod(VERIFY_CODE);
		get.setRequestHeader(USER_AGENT_H, USER_AGENT);
		get.setRequestHeader("Cookie", this.cookiestr);
		NameValuePair[] params = new NameValuePair[] { new NameValuePair("username", this.loginUser),
				new NameValuePair("r", "1365318662649") };
		get.setQueryString(params);
		int status = client.executeMethod(get);
		if (status == HttpStatus.SC_OK) {
			return get.getResponseBodyAsStream();
		}
		return null;
	}

	/**
	 * 获得粉丝数量,如果解析成功会返回粉丝数，如果解析失败则返回-1
	 * 
	 * @return
	 */
	public int getFansCount() {
		try {
			String paramStr = "?t=wxm-friend&token=" + this.token + "&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0";
			if (!this.isLogin) {
				this._login();
			}
			if (this.isLogin) {
				GetMethod get = new GetMethod(CONTACT_MANAGE_PAGE + paramStr);
				get.setRequestHeader(REFERER_H, INDEX_URL);
				get.setRequestHeader("Cookie", this.cookiestr);
				int status = client.executeMethod(get);
				if (status == HttpStatus.SC_OK) {
					return parseFansCount(get.getResponseBodyAsString());
				}
				return -1;
			}
		} catch (Exception e) {
			String info = "【获取粉丝数失败】【可能登录过期】";
			System.err.println(info);
			log.debug(info);
			log.info(info);
			return -1;
		}
		return -1;
	}

	/**
	 * 从返回文本中提取粉丝数量
	 * 
	 * @param text
	 * @return
	 */
	private int parseFansCount(String text) {
		try {
			StringBuffer json = new StringBuffer();
			final String start = "DATA.groupList =";
			for (int i = text.indexOf(start) + start.length(), len = text.length(); i < len; i++) {
				char ci = text.charAt(i);
				if (ci == ';') {
					break;
				}
				json.append(text.charAt(i));
			}
			String txt = json.toString().replaceAll("[*]1", "").replaceAll("defaultGroupName\\[0\\] \\|\\|", "")
					.replaceAll("defaultGroupName\\[1\\] \\|\\|", "").replaceAll("defaultGroupName\\[2\\] \\|\\|", "")
					.replaceAll("defaultGroupName\\[100\\] \\|\\|", "");
			List<FansCount> fans = JSON.parseArray(txt, FansCount.class);
			if (null != fans && !fans.isEmpty())
				for (FansCount fan : fans)
					if (fan.getId() == 0)
						return fan.getNum();
		} catch (Exception e) {
			String info = "【解析粉丝数失败】 " + "\t\n【文本：】\t\n" + text + "\t\n" + "【发生异常：" + e.getMessage() + "】";
			System.err.println(info);
			log.debug(info);
			log.info(info);
			return -1;
		}
		return -1;
	}

	/**
	 * 
	 * <strong>群发消息</strong>
	 * <p>
	 * 返回码说明<br>
	 * 0：发送成功<br>
	 * 64004:今天的群发数量已到，无法群发<br>
	 * -20000:请求被禁止，请仔细检查token是否合法<br>
	 * </p>
	 * <p>
	 * 可通过msgSendCode取得发送状态码
	 * </p>
	 * 
	 * @by liaokai
	 * 
	 */
	/**
	 * @param form
	 * @param type
	 * @return
	 */
	public boolean msgSend(MsgForm form, MsgType type) {
		try {
			if (!this.isLogin) {
				this._login();
			}
			if (this.isLogin) {
				form.setToken(this.token);
				PostMethod post = new PostMethod(POST_MSG);
				post.setRequestHeader(USER_AGENT_H, USER_AGENT);
				post.setRequestHeader(REFERER_H, INDEX_URL);
				post.setRequestHeader("Cookie", this.cookiestr);
				NameValuePair[] params = null;
				Part[] parts = null;
				switch (type) {
				case TEXT:
					// params = new NameValuePair[] {
					// new NameValuePair("type", form.getType()),
					// new NameValuePair("error", form.getError()),
					// new NameValuePair("needcomment",
					// form.getNeedcomment()),
					// new NameValuePair("groupid", form.getGroupid()),
					// new NameValuePair("sex", form.getSex()),
					// new NameValuePair("country", form.getCountry()),
					// new NameValuePair("province", form.getProvince()),
					// new NameValuePair("city", form.getCity()),
					// new NameValuePair("token", form.getToken()),
					// new NameValuePair("ajax", form.getAjax()),
					// new NameValuePair("t", "ajax-response") };
					parts = new Part[] { new StringPart("content", form.getContent(), "UTF-8"),
							new StringPart("type", MsgType.TEXT.getType() + ""), new StringPart("error", form.getError()),
							new StringPart("needcomment", form.getNeedcomment()), new StringPart("groupid", form.getGroupid()),
							new StringPart("sex", form.getSex()), new StringPart("country", form.getCountry()),
							new StringPart("province", form.getProvince()), new StringPart("city", form.getCity()),
							new StringPart("token", form.getToken()), new StringPart("ajax", form.getAjax()),
							new StringPart("t", "ajax-response") };
					break;
				case IMAGE_TEXT:
					ImgTextMsgList list = this.getImgTextMsgList();
					List<ImgTextMsgList.ImgTextMsg> imgTextMsgs = list.getList();
					if (null != imgTextMsgs && !imgTextMsgs.isEmpty()) {
						ImgTextMsgList.ImgTextMsg imgTextMsg = imgTextMsgs.get(0);
						if (null != imgTextMsg) {
							form.setAppmsgid(imgTextMsg.getAppId());
							form.setFid(imgTextMsg.getAppId());
						}
					}
					if (StringUtils.isBlank(form.getAppmsgid()) || StringUtils.isBlank(form.getFid())) {
						this.msgSendMsg = "参数错误:appmsgid为空";
						return false;
					}
					parts = new Part[] { new StringPart("fid", form.getFid(), "UTF-8"),
							new StringPart("appmsgid", form.getAppmsgid(), "UTF-8"),
							new StringPart("type", MsgType.IMAGE_TEXT.getType() + ""), new StringPart("error", form.getError()),
							new StringPart("needcomment", form.getNeedcomment()), new StringPart("groupid", form.getGroupid()),
							new StringPart("sex", form.getSex()), new StringPart("country", form.getCountry()),
							new StringPart("province", form.getProvince()), new StringPart("city", form.getCity()),
							new StringPart("token", form.getToken()), new StringPart("ajax", form.getAjax()),
							new StringPart("t", "ajax-response") };
					break;
				default:
					parts = new Part[] { new StringPart("content", form.getContent(), "UTF-8"), new StringPart("type", form.getType()),
							new StringPart("error", form.getError()), new StringPart("needcomment", form.getNeedcomment()),
							new StringPart("groupid", form.getGroupid()), new StringPart("sex", form.getSex()),
							new StringPart("country", form.getCountry()), new StringPart("province", form.getProvince()),
							new StringPart("city", form.getCity()), new StringPart("token", form.getToken()),
							new StringPart("ajax", form.getAjax()), new StringPart("t", "ajax-response") };

					break;
				}
				RequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
				post.setRequestEntity(entity);
				int status;
				status = client.executeMethod(post);
				if (status == HttpStatus.SC_OK) {
					String text = post.getResponseBodyAsString();
					try {
						MsgJson ret = JSON.parseObject(text, MsgJson.class);
						this.msgSendCode = ret.getRet();
						switch (this.msgSendCode) {
						case 0:
							this.msgSendMsg = "发送成功";
							return true;
						case -2:
							this.msgSendMsg = "参数错误，请仔细检查";
							return false;
						case 64004:
							this.msgSendMsg = "今天的群发数量已到，无法群发";
							return false;
						case -20000:
							this.msgSendMsg = "请求被禁止，请仔细检查token是否合法";
							return false;
						default:
							this.msgSendMsg = "未知错误!";
							return false;
						}
					} catch (Exception e) {
						String info = "【群发信息失败】【解析json错误】" + e.getMessage() + "\n\t【文本:】\n\t" + text;
						System.err.println(info);
						log.debug(info);
						log.info(info);
						return false;
					}
				}
			}
		} catch (Exception e) {
			String info = "【群发信息失败】" + e.getMessage();
			System.err.println(info);
			log.debug(info);
			log.info(info);
			return false;
		}
		return false;
	}

	private String updateImgErr;

	public String getUpdateImgErr() {
		return updateImgErr;
	}

	public void setUpdateImgErr(String updateImgErr) {
		this.updateImgErr = updateImgErr;
	}

	// public final static Pattern IMG_SUCCESS_REG =
	// Pattern.compile("\.top\.W\.upload\.suc(\"")
	public String updateImg(ImgFileForm form) {
		try {
			if (!this.isLogin)
				this.isLogin();
			if (this.isLogin) {
				form.setToken(this.getToken());
				PostMethod post = new PostMethod(UPLOAD_MATERIAL);
				post.setRequestHeader(USER_AGENT_H, USER_AGENT);
				post.setRequestHeader(REFERER_H, INDEX_URL);
				post.setRequestHeader("Connection", "Keep-Alive");
				post.setRequestHeader("Cookie", this.cookiestr);
				post.setRequestHeader("Cache-Control", "no-cache");

				/**
				 * private String cgi = "uploadmaterial"; private String type =
				 * "2"; private String token = ""; private String t =
				 * "iframe-uploadfile"; private String lang = "zh_CN"; private
				 * String formId = "1";
				 */
				String fileName = form.getUploadfile().getName();
				String ext = StringUtils.substring(fileName, fileName.indexOf("."), fileName.length());
				FilePart file = new FilePart("uploadfile", form.getUploadfile(), "image/" + ext, "UTF-8");
				System.out.println(form.getToken());
				Part[] parts = new Part[] { new StringPart("cgi", form.getCgi()), new StringPart("type", form.getType()),
						new StringPart("token", form.getToken()), new StringPart("t", form.getT()), new StringPart("lang", form.getLang()),
						new StringPart("formId", form.getFormId()), file };
				MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
				post.setRequestEntity(entity);
				int status = client.executeMethod(post);
				if (status == HttpStatus.SC_OK) {
					String text = post.getResponseBodyAsString();
					return parseUploadImgText(text);
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String parseUploadImgText(String text) {
		try {
			if (StringUtils.isBlank(text))
				return null;
			String sub = null;
			int type = 0;
			if (text.indexOf("top.W.upload.suc(") != -1) {
				sub = "top.W.upload.suc(";
				type = 1;
			} else if (text.indexOf("top.W.upload.err(") != -1) {
				sub = "top.W.upload.err(";
				type = 2;
			}
			StringBuffer ret = new StringBuffer();
			for (int i = text.indexOf(sub) + sub.length(), len = text.length(); i < len; i++) {
				char c = text.charAt(i);
				if (c == ')')
					break;
				ret.append(c);
			}
			String result = ret.toString().replaceAll("['|\"]", "");
			String[] s = null;
			switch (type) {
			case 1:
				s = StringUtils.split(result, ",");
				if (null != s && s.length == 4) {
					this.updateImgErr = StringUtils.trim(s[0]);
					return StringUtils.trim(s[3]);
				}
				this.updateImgErr = "未知错误";
				return null;
			case 2:
				s = StringUtils.split(result, ",");
				if (null != s && s.length == 3) {
					this.updateImgErr = StringUtils.trim(s[0]);
					return null;
				}
				this.updateImgErr = "未知错误";
				return null;
			default:
				this.updateImgErr = "未知错误";
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private String imgTextSendErr = "";
	private int imgTextSendCode;

	public String getImgTextSendErr() {
		return imgTextSendErr;
	}

	public void setImgTextSendErr(String imgTextSendErr) {
		this.imgTextSendErr = imgTextSendErr;
	}

	public int getImgTextSendCode() {
		return imgTextSendCode;
	}

	public void setImgTextSendCode(int imgTextSendCode) {
		this.imgTextSendCode = imgTextSendCode;
	}

	public boolean saveImgText(ImgTextForm form) {
		try {
			if (!this.isLogin)
				this.isLogin();
			if (this.isLogin) {
				form.setToken(this.getToken());
				PostMethod post = new PostMethod(OPERATE_APPMSG);
				post.setRequestHeader(USER_AGENT_H, USER_AGENT);
				post.setRequestHeader(REFERER_H, INDEX_URL);

				post.setRequestHeader("Cookie", this.cookiestr);

				/**
				 * private String error = "false"; private String count; private
				 * String AppMsgId = ""; private String token; private String
				 * ajax = "1"; private String lang = "zh_CN"; private String t =
				 * "ajax-response"; private String sub = "create"; private
				 * List<Piece> pieces;
				 */

				System.out.println(form.getToken());
				List<Part> params = new ArrayList<Part>();
				params.add(new StringPart("error", form.getError()));
				params.add(new StringPart("count", form.getCount()));
				params.add(new StringPart("AppMsgId", form.getAppMsgId()));
				params.add(new StringPart("token", form.getToken()));
				params.add(new StringPart("ajax", form.getAjax()));
				params.add(new StringPart("lang", form.getLang()));
				params.add(new StringPart("t", form.getT()));
				params.add(new StringPart("sub", form.getSub()));

				int i = 0;
				for (ImgTextForm.Piece piece : form.getPieces()) {
					if (null != piece.getImg()) {
						String fileid = this.updateImg(piece.getImg());
						if (StringUtils.isBlank(fileid))
							continue;
						piece.setFileid(fileid);
						params.add(new StringPart("title" + i, piece.getTitle(), UTF_8));
						params.add(new StringPart("digest" + i, piece.getDigest(), UTF_8));
						params.add(new StringPart("content" + i, piece.getContent(), UTF_8));
						params.add(new StringPart("fileid" + i, piece.getFileid(), UTF_8));
						i++;
					}
				}
				Part[] parts = new Part[params.size()];
				MultipartRequestEntity entity = new MultipartRequestEntity(params.toArray(parts), post.getParams());
				post.setRequestEntity(entity);
				int status = client.executeMethod(post);
				if (status == HttpStatus.SC_OK) {
					String text = post.getResponseBodyAsString();
					try {
						MsgJson ret = JSON.parseObject(text, MsgJson.class);
						this.imgTextSendCode = ret.getRet();
						System.out.println(text);
						switch (this.msgSendCode) {
						case 0:
							this.imgTextSendErr = "发送成功";
							return true;
						case -2:
							this.imgTextSendErr = "参数错误，请仔细检查";
							return false;
						case 64004:
							this.imgTextSendErr = "今天的群发数量已到，无法群发";
							return false;
						case -20000:
							this.imgTextSendErr = "请求被禁止，请仔细检查token是否合法";
							return false;
						default:
							this.imgTextSendErr = "未知错误!";
							return false;
						}
					} catch (Exception e) {
						String info = "【群发信息失败】【解析json错误】" + e.getMessage() + "\n\t【文本:】\n\t" + text;
						System.err.println(info);
						log.debug(info);
						log.info(info);
						return false;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ImgTextMsgList getImgTextMsgList() {
		try {
			if (!this.isLogin)
				this.isLogin();
			if (this.isLogin) {

				GetMethod post = new GetMethod(OPERATE_APPMSG);
				post.setRequestHeader(USER_AGENT_H, USER_AGENT);
				post.setRequestHeader(REFERER_H, INDEX_URL);
				post.setRequestHeader("Connection", "Keep-Alive");
				post.setRequestHeader("Cookie", this.cookiestr);
				post.setRequestHeader("Cache-Control", "no-cache");
				/**
				 * sub=list&type=10&subtype=3&t=wxm-appmsgs-list-new&pagesize=10
				 * &pageidx=0&token=1004476860&lang=zh_CN
				 */
				NameValuePair[] params = new NameValuePair[] { new NameValuePair("sub", "list"), new NameValuePair("type", "10"),
						new NameValuePair("subtype", "3"), new NameValuePair("t", "wxm-appmsgs-list-new"),
						new NameValuePair("pagesize", "10"), new NameValuePair("pageidx", "0"),
						new NameValuePair("token", this.getToken()), new NameValuePair("lang", "zh_CN") };
				post.setQueryString(params);

				int status = client.executeMethod(post);
				if (status == HttpStatus.SC_OK) {
					String text = post.getResponseBodyAsString();
					Document doc = Jsoup.parse(text);
					Elements eles = doc.select("script[id=json-msglist]");
					for (Element e : eles) {
						String html = e.html();
						ImgTextMsgList ret = JSON.parseObject(html, ImgTextMsgList.class);
						return ret;
					}

				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5加密
	 * 
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
	 * 页面跳转
	 * 
	 * @param url
	 */

	public void redirect(String url) {
		if (url.indexOf("https://") == -1)
			url = HOST + url;
		try {
			if (this.isLogin) {
				GetMethod get = new GetMethod(url);
				get.setRequestHeader(USER_AGENT_H, USER_AGENT);
				get.setRequestHeader(REFERER_H, INDEX_URL);
				get.setRequestHeader("Cookie", this.cookiestr);
				int status = client.executeMethod(get);
				if (status == HttpStatus.SC_OK) {
					System.err.println("正在跳转.....");
					System.out.println(get.getResponseBodyAsString());
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 使用方法:<br>
	 * new Weixin()对象，先登录再取粉丝数或者群发。<br>
	 * 群发需要传入一个MsgForm参数： 默认发送文本消息，发送给中国区<br>
	 * 所以不需要再设置其他参数，调用setContent将需要发送的内容填充就OK<br>
	 * 内容中的超链接可以直接发送不用使用<a>标签
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String LOGIN_USER = "2334999724@qq.com";
		String LOGIN_PWD = "zhouzhouzhou";
		Weixin wx = new Weixin(LOGIN_USER, LOGIN_PWD);
		wx.login();
		// wx.getMsgTextList();
		wx.getCookiestr();
		/*
		 * File file = new File("D:\\Data\\image\\4.jpg"); ImgFileForm img = new
		 * ImgFileForm(); img.setUploadfile(file); ImgTextForm form = new
		 * ImgTextForm(); List<ImgTextForm.Piece> pieces = new
		 * ArrayList<ImgTextForm.Piece>();
		 * 
		 * ImgTextForm.Piece piece = new ImgTextForm.Piece();
		 * piece.setContent("第一个第一个第一个第一个第一个第一个"); piece.setDigest("第一个第一个");
		 * piece.setImg(img); piece.setTitle("第一个"); pieces.add(piece);
		 * 
		 * ImgTextForm.Piece piece1 = new ImgTextForm.Piece();
		 * piece1.setContent("第二个第二个第二个第二个第二个第二个"); piece1.setDigest("第二个第二个");
		 * piece1.setImg(img); piece1.setTitle("第二个"); pieces.add(piece1);
		 * 
		 * ImgTextForm.Piece piece2 = new ImgTextForm.Piece();
		 * piece2.setContent("第三个第三个第三个第三个第三个第三个"); piece2.setDigest("第三个第三个");
		 * piece2.setImg(img); piece2.setTitle("第三个"); pieces.add(piece2);
		 * 
		 * form.setPieces(pieces); wx.saveImgText(form); MsgForm msg = new
		 * MsgForm(); wx.msgSend(msg, MsgType.IMAGE_TEXT);
		 */
		// System.out.println(wx.getMsgSendMsg());
		System.out.println(wx.getFansCount());
		// System.out.println(wx.getImgTextSendErr());

	}
}
