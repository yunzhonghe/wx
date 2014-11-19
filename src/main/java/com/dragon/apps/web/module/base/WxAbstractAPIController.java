package com.dragon.apps.web.module.base;

import static com.dragon.spider.util.BeanUtil.isNull;
import static com.dragon.spider.util.BeanUtil.nonNull;
import static com.dragon.spider.util.CollectionUtil.isEmpty;
import static com.dragon.spider.util.CollectionUtil.isNotEmpty;
import static com.dragon.spider.util.StrUtil.isNotBlank;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.service.MessageHandleService;
import com.dragon.apps.service.WxFansHandleService;
import com.dragon.spider.handle.EventHandle;
import com.dragon.spider.handle.MessageHandle;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.BaseReq;
import com.dragon.spider.message.req.BaseReqMsg;
import com.dragon.spider.message.req.EventType;
import com.dragon.spider.message.req.ImageReqMsg;
import com.dragon.spider.message.req.LinkReqMsg;
import com.dragon.spider.message.req.LocationEvent;
import com.dragon.spider.message.req.LocationReqMsg;
import com.dragon.spider.message.req.MenuEvent;
import com.dragon.spider.message.req.QrCodeEvent;
import com.dragon.spider.message.req.ReqType;
import com.dragon.spider.message.req.TextReqMsg;
import com.dragon.spider.message.req.VideoReqMsg;
import com.dragon.spider.message.req.VoiceReqMsg;
import com.dragon.spider.util.MessageUtil;
import com.dragon.spider.util.SignUtil;
import com.jfinal.core.Controller;

/**
 * @author chenlong 预留一个抽象的Controller 可以把一些公用的逻辑放到里面
 */
public abstract class WxAbstractAPIController extends Controller {

	private final static boolean isDevModel = false;

	/**
	 * 微信消息处理器列表
	 */
	private static List<MessageHandle> messageHandles;

	/**
	 * 微信事件处理器列表
	 */
	private static List<EventHandle> eventHandles;

	/**
	 * 子类重写，加入自定义的微信消息处理器，细化消息的处理
	 * 
	 * @return 微信消息处理器列表
	 */
	protected List<MessageHandle> getMessageHandles() {
		return null;
	}

	/**
	 * 子类重写，加入自定义的微信事件处理器，细化消息的处理
	 * 
	 * @return 微信事件处理器列表
	 */
	protected List<EventHandle> getEventHandles() {
		return null;
	}

	/**
	 * 子类提供token用于绑定微信公众平台
	 * 
	 * @return token值
	 */
	protected String getToken() {
		return "yjkkop1412736255";
	};

	/**
	 * 处理微信服务器发来的请求方法
	 * 
	 * @param request
	 *            http请求对象
	 * @return 处理消息的结果，已经是接口要求的xml报文了
	 */
	protected String processRequest(HttpServletRequest request) {

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("FromUserName", "chenlong");
		reqMap.put("ToUserName", "liujian");
		reqMap.put("MsgType", "text");
		reqMap.put("Content", "helllofasdfa");
		reqMap.put("MsgId", "1234567890123456");
		reqMap.put("CreateTime", String.valueOf(Calendar.getInstance().getTimeInMillis()));
		if (!isDevModel) {
			reqMap = MessageUtil.parseXml(request);
		}

		String fromUserName = reqMap.get("FromUserName");
		String toUserName = reqMap.get("ToUserName");
		String msgType = reqMap.get("MsgType");

		BaseMsg msg = null;

		if (msgType.equals(ReqType.EVENT)) {
			String eventType = reqMap.get("Event");
			String ticket = reqMap.get("Ticket");
			if (isNotBlank(ticket)) {
				String eventKey = reqMap.get("EventKey");
				QrCodeEvent event = new QrCodeEvent(eventKey, ticket);
				buildBasicEvent(reqMap, event);
				WxFansHandleService service = new WxFansHandleService();
				msg = service.handleQrCodeEvent(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			}
			if (eventType.equals(EventType.SUBSCRIBE)) {
				BaseEvent event = new BaseEvent();
				buildBasicEvent(reqMap, event);
				WxFansHandleService service = new WxFansHandleService();
				msg = service.handleSubscribe(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			} else if (eventType.equals(EventType.UNSUBSCRIBE)) {
				BaseEvent event = new BaseEvent();
				buildBasicEvent(reqMap, event);
				WxFansHandleService service = new WxFansHandleService();
				msg = service.handleUnsubscribe(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			} else if (eventType.equals(EventType.CLICK)) {
				String eventKey = reqMap.get("EventKey");
				MenuEvent event = new MenuEvent(eventKey);
				buildBasicEvent(reqMap, event);
				msg = handleMenuClickEvent(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			} else if (eventType.equals(EventType.VIEW)) {
				String eventKey = reqMap.get("EventKey");
				MenuEvent event = new MenuEvent(eventKey);
				buildBasicEvent(reqMap, event);
				msg = handleMenuViewEvent(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			} else if (eventType.equals(EventType.LOCATION)) {
				double latitude = Double.parseDouble(reqMap.get("Latitude"));
				double longitude = Double.parseDouble(reqMap.get("Longitude"));
				double precision = Double.parseDouble(reqMap.get("Precision"));
				LocationEvent event = new LocationEvent(latitude, longitude, precision);
				buildBasicEvent(reqMap, event);
				msg = handleLocationEvent(event);
				if (isNull(msg)) {
					msg = processEventHandle(event);
				}
			}
		} else {
			if (msgType.equals(ReqType.TEXT)) {
				String content = reqMap.get("Content");
				TextReqMsg textReqMsg = new TextReqMsg(content);
				MessageHandleService service = new MessageHandleService();
				buildBasicReqMsg(reqMap, textReqMsg);
				service.handleTextMsg(textReqMsg);
				msg = handleTextMsg(textReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(textReqMsg);
				}
			} else if (msgType.equals(ReqType.IMAGE)) {
				String picUrl = reqMap.get("PicUrl");
				String mediaId = reqMap.get("MediaId");
				ImageReqMsg imageReqMsg = new ImageReqMsg(picUrl, mediaId);
				buildBasicReqMsg(reqMap, imageReqMsg);
				MessageHandleService service = new MessageHandleService();
				service.handleImageMsg(imageReqMsg);
				msg = handleImageMsg(imageReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(imageReqMsg);
				}
			} else if (msgType.equals(ReqType.VOICE)) {
				String format = reqMap.get("Format");
				String mediaId = reqMap.get("MediaId");
				String recognition = reqMap.get("Recognition");
				VoiceReqMsg voiceReqMsg = new VoiceReqMsg(mediaId, format, recognition);
				buildBasicReqMsg(reqMap, voiceReqMsg);

				MessageHandleService service = new MessageHandleService();
				service.handleVoiceMsg(voiceReqMsg);

				msg = handleVoiceMsg(voiceReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(voiceReqMsg);
				}
			} else if (msgType.equals(ReqType.VIDEO)) {
				String thumbMediaId = reqMap.get("ThumbMediaId");
				String mediaId = reqMap.get("MediaId");
				VideoReqMsg videoReqMsg = new VideoReqMsg(mediaId, thumbMediaId);
				buildBasicReqMsg(reqMap, videoReqMsg);

				MessageHandleService service = new MessageHandleService();
				service.handleVideoMsg(videoReqMsg);

				msg = handleVideoMsg(videoReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(videoReqMsg);
				}
			} else if (msgType.equals(ReqType.LOCATION)) {
				double locationX = Double.parseDouble(reqMap.get("Location_X"));
				double locationY = Double.parseDouble(reqMap.get("Location_Y"));
				int scale = Integer.parseInt(reqMap.get("Scale"));
				String label = reqMap.get("Label");
				LocationReqMsg locationReqMsg = new LocationReqMsg(locationX, locationY, scale, label);
				buildBasicReqMsg(reqMap, locationReqMsg);

				MessageHandleService service = new MessageHandleService();
				service.handleLocationMsg(locationReqMsg);

				msg = handleLocationMsg(locationReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(locationReqMsg);
				}
			} else if (msgType.equals(ReqType.LINK)) {
				String title = reqMap.get("Title");
				String description = reqMap.get("Description");
				String url = reqMap.get("Url");
				LinkReqMsg linkReqMsg = new LinkReqMsg(title, description, url);
				buildBasicReqMsg(reqMap, linkReqMsg);

				MessageHandleService service = new MessageHandleService();
				service.handleLinkMsg(linkReqMsg);

				msg = handleLinkMsg(linkReqMsg);
				if (isNull(msg)) {
					msg = processMessageHandle(linkReqMsg);
				}
			}
		}
		String result = "";
		if (nonNull(msg)) {
			msg.setFromUserName(toUserName);
			msg.setToUserName(fromUserName);
			result = msg.toXml();
		}
		return result;
	}

	// 充当锁
	private static final Object lock = new Object();

	private BaseMsg processMessageHandle(BaseReqMsg msg) {
		if (isEmpty(messageHandles)) {
			synchronized (lock) {
				messageHandles = this.getMessageHandles();
			}
		}
		if (isNotEmpty(messageHandles)) {
			for (MessageHandle messageHandle : messageHandles) {
				BaseMsg resultMsg = messageHandle.handle(msg);
				if (nonNull(resultMsg)) {
					return resultMsg;
				}
			}
		}
		return null;
	}

	private BaseMsg processEventHandle(BaseEvent event) {
		if (isEmpty(eventHandles)) {
			synchronized (lock) {
				eventHandles = this.getEventHandles();
			}
		}
		if (isNotEmpty(eventHandles)) {
			for (EventHandle eventHandle : eventHandles) {
				BaseMsg resultMsg = eventHandle.handle(event);
				if (nonNull(resultMsg)) {
					return resultMsg;
				}
			}
		}
		return null;
	}

	/**
	 * 处理文本消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		/*
		 * if(null==msg){ return null; }else{ return msg.getContent(); }
		 */
		TextMsg tReqMsg = new TextMsg("vbafaksdjbakdfad");
		tReqMsg.setMsgType("Text");
		return tReqMsg;
	}

	/**
	 * 处理图片消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleImageMsg(ImageReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理语音消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleVoiceMsg(VoiceReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理视频消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleVideoMsg(VideoReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理地理位置消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLocationMsg(LocationReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理链接消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLinkMsg(LinkReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理地理位置事件，有需要时子类重写
	 * 
	 * @param event
	 *            地理位置事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLocationEvent(LocationEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理菜单点击事件，有需要时子类重写
	 * 
	 * @param event
	 *            菜单点击事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理菜单跳转事件，有需要时子类重写
	 * 
	 * @param event
	 *            菜单跳转事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleMenuViewEvent(MenuEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理添加关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            添加关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleSubscribe(BaseEvent event) {
		return new TextMsg("感谢您的关注!");
	}

	protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理取消关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            取消关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		return null;
	}

	protected BaseMsg handleDefaultMsg(BaseReqMsg msg) {
		return null;
	}

	protected BaseMsg handleDefaultEvent(BaseEvent event) {
		return null;
	}

	private void buildBasicReqMsg(Map<String, String> reqMap, BaseReqMsg reqMsg) {
		addBasicReqParams(reqMap, reqMsg);
		reqMsg.setMsgId(reqMap.get("MsgId"));
	}

	private void buildBasicEvent(Map<String, String> reqMap, BaseEvent event) {
		addBasicReqParams(reqMap, event);
		event.setEvent(reqMap.get("Event"));
	}

	private void addBasicReqParams(Map<String, String> reqMap, BaseReq req) {
		req.setMsgType(reqMap.get("MsgType"));
		req.setFromUserName(reqMap.get("FromUserName"));
		req.setToUserName(reqMap.get("ToUserName"));
		req.setCreateTime(Long.parseLong(reqMap.get("CreateTime")));
	}

	protected boolean isLegal(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String token = this.getToken();
		return SignUtil.checkSignature("yjkkop1412736255", signature, timestamp, nonce);
	}

	@Override
	public void renderError(int errorCode) {
		renderText(String.valueOf(errorCode));
	}
}
