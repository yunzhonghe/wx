package com.dragon.adapter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.BaseReqMsg;
/**
 * 处理消息、事件等的标记缓存，主要是为了实现排重功能；网络较慢情况下，微信服务器会尝试发送三遍而导致重复。
 * @author LiuJian
 */
public class WxHandleCache {
	private MessageHandle messageHandle = null;
	private EventHandle eventHandle = null;
	private WxHandleCache(){
		messageHandle = new MessageHandle();
		eventHandle = new EventHandle();
	}
	public boolean hasMessageBeenHandled(BaseReqMsg message){
		return messageHandle.hasMessageBeenHandled(message);
	}
	public boolean hasEventBeenHandled(BaseEvent event){
		return eventHandle.hasEventBeenHandled(event);
	}
	
	private class MessageHandle{
		private CacheConfig config = new CacheConfig(10000);
		//(消息排重，推荐使用msgid排重)
		public boolean hasMessageBeenHandled(BaseReqMsg message){
			if(message==null){
				return true;
			}else{
				//踢重检查
				String _key = message.getMsgId();
				return hasKeyBeenHandledByCache(_key, config);
			}
		}
	}
	private class EventHandle{
		private CacheConfig config = new CacheConfig(10000);
		//(事件排重，推荐使用FromUserName + CreateTime 排重)
		public boolean hasEventBeenHandled(BaseEvent event){
			if(event==null){
				return true;
			}else{
				//踢重检查(事件排重，推荐使用FromUserName + CreateTime 排重)
				String _key = event.getFromUserName()+event.getCreateTime();
				return hasKeyBeenHandledByCache(_key, config);
			}
		}
	}
	private class CacheConfig{
		private Integer maxCapacity;
		private AtomicInteger count;
		private List<String> cache;
		private Boolean cacheIsFull;
		private CacheConfig(Integer maxCapacity){
			this.maxCapacity = maxCapacity;
			this.count = new AtomicInteger(0);
			this.cache = new ArrayList<String>(this.maxCapacity);
			this.cacheIsFull = false;
		}
		public Integer getMaxCapacity() {
			return maxCapacity;
		}
		public AtomicInteger getCount() {
			return count;
		}
		public List<String> getCache() {
			return cache;
		}
		public Boolean getCacheIsFull() {
			return cacheIsFull;
		}
		public void setCacheIsFull(Boolean cacheIsFull) {
			this.cacheIsFull = cacheIsFull;
		}
	}
	private static boolean hasKeyBeenHandledByCache(String _key,CacheConfig config){
		if(config.getCache().indexOf(_key)>-1){//已经处理过，数据重复.
			return true;
		}else{
			int _index = config.getCount().getAndIncrement();
			if(_index>=config.getMaxCapacity()){
				if(!config.getCacheIsFull()){
					config.setCacheIsFull(true);
				}
				synchronized (config.getCount()) {
					if(_index>=config.getMaxCapacity()){
						_index = _index%config.getMaxCapacity();
						if(config.getCount().get()>config.getMaxCapacity()){
							setAtomicIntegerModeCapacity(config.getCount(), config.getMaxCapacity());
						}
					}
				}
			}
			if(config.getCacheIsFull()){
				config.getCache().set(_index, _key);
			}else{
				synchronized(config.getCache()){
					config.getCache().add(_index, _key);
				}
			}
		}
		return false;
	}
	private static void setAtomicIntegerModeCapacity(AtomicInteger atomicInteger, int capacity){
		for (;;){
            int current = atomicInteger.get();
            if (atomicInteger.compareAndSet(current, current%capacity)){
            	break;
            }
        }
	}
	
	//单例
	private static WxHandleCache instance = null;
	public static WxHandleCache getInstance(){
		if(instance==null){
			synchronized (WxHandleCache.class) {
				if(instance==null)
					instance = new WxHandleCache();
			}
		}
		return instance;
	}
}
