package com.dragon.spider.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.Logger;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.httpclient.RequestModel;

public class HttpClientAccountApi extends HttpClientBaseAPI {

	public HttpClientAccountApi(HttpClientApiConfig config) {
		super(config);
	}

	private String url = "https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index&action=index&lang=zh_CN&token=#token";
	private String domain = "https://mp.weixin.qq.com";

	public WxAccount getDetailAccount(WxAccount account) {

		if (null == config.getToken()) {
			refreshToken();
		}
		Logger.info(this, "begin to init wx account " + config.toString());
		String url = this.url;
		RequestModel model = new RequestModel();
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("Referer", "https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=" + config.getToken());
		model.setHeaders(paras);
		model.setUrl(url);
		try {
			String returnMsg = simpleGetInvoke(model);
			Test.writeNewUrlFile(returnMsg);
			System.out.println(returnMsg);
			Document doc = Jsoup.parse(returnMsg);
			Elements eles = doc.getElementsByClass("meta_content");

			if (null != eles && eles.size() == 11) {
				/* WxAccount account = new WxAccount(); */
				account.setName(eles.get(0).html().trim());
				account.setAvatar(eles.get(1).html().trim());
				account.setAccount(eles.get(2).html().trim());

				// ID
				String originalidStr = eles.get(3).html().trim();
				if (null != originalidStr) {
					String originalid = originalidStr.replace("<span>", "").replace("</span>", "");
					account.setOriginalid(originalid);
				}

				// wx号
				String wxNumberStr = eles.get(4).html().trim();
				if (null != wxNumberStr) {
					String wxNumber = wxNumberStr.replace("<span>", "").replace("</span>", "");
					account.setWxNumber(wxNumber);
				}

				// Type 设置，订阅号Or服务号
				String type = eles.get(5).html().trim();
				WxAccType accType = new WxAccType();
				List<WxAccType> accTypes = accType.getWxAccTypeList();
				for (int i = 0; i < accTypes.size(); i++) {
					if (accTypes.get(i).getName().equals(type)) {
						account.setTypeid(accTypes.get(i).getId());
					}
				}
				account.setFunctions(eles.get(8).html().trim());

				// 二维码设置
				String avatarStr = eles.get(10).html();
				Document avaDoc = Jsoup.parse(avatarStr);
				Elements avaEles = avaDoc.getElementsByAttribute("src");
				if (avaEles != null && avaEles.size() == 1) {
					String avator = domain + avaEles.get(0).attr("src");
					account.setAvatar(avator);
				}

				return account;
			} else {
				Logger.info(this, "init failed");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
