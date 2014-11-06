package com.dragon.spider.handle;

import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.req.BaseEvent;

/**
 * 微信事件处理器接口
 * @author peiyu
 * @since 1.1
 */
public interface EventHandle {
    /**
     * 处理微信事件
     * @param event 微信事件
     * @return 回复用户的消息
     */
    BaseMsg handle(BaseEvent event);
}
