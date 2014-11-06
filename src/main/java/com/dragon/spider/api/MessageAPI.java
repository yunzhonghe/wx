package com.dragon.spider.api;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.message.*;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息相关API
 * @author peiyu
 */
public class MessageAPI extends BaseAPI {

    public MessageAPI(ApiConfig config) {
        super(config);
    }

    /**
     * 发布客服消息
     * @param openid 关注者ID
     * @param message 消息对象，支持各种消息类型
     */
    public void sendCustomMessage(String openid, BaseMsg message) {
        BeanUtil.requireNonNull(openid, "openid is null");
        BeanUtil.requireNonNull(message, "message is null");
        String url = BASE_API_URL + "cgi-bin/message/custom/send?access_token=#";
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("touser",openid);
        if(message instanceof TextMsg) {
            TextMsg msg = (TextMsg) message;
            params.put("msgtype","text");
            Map<String, String> text = new HashMap<String, String>();
            text.put("content", msg.getContent());
            params.put("text", text);
        }
        else if(message instanceof ImageMsg) {
            ImageMsg msg = (ImageMsg) message;
            params.put("msgtype", "image");
            Map<String, String> image = new HashMap<String, String>();
            image.put("media_id", msg.getMediaId());
            params.put("image", image);
        }
        else if(message instanceof VoiceMsg) {
            VoiceMsg msg = (VoiceMsg) message;
            params.put("msgtype", "voice");
            Map<String, String> voice = new HashMap<String, String>();
            voice.put("media_id", msg.getMediaId());
            params.put("voice", voice);
        }
        else if(message instanceof VideoMsg) {
            VideoMsg msg = (VideoMsg) message;
            params.put("msgtype", "video");
            Map<String, String> video = new HashMap<String, String>();
            video.put("media_id", msg.getMediaId());
            video.put("thumb_media_id", msg.getMediaId());
            video.put("title", msg.getTitle());
            video.put("description", msg.getDescription());
            params.put("video", video);
        }
        else if(message instanceof MusicMsg) {
            MusicMsg msg = (MusicMsg) message;
            params.put("msgtype", "music");
            Map<String, String> music = new HashMap<String, String>();
            music.put("thumb_media_id", msg.getThumbMediaId());
            music.put("title", msg.getTitle());
            music.put("description", msg.getDescription());
            music.put("musicurl", msg.getMusicUrl());
            music.put("hqmusicurl", msg.getHqMusicUrl());
            params.put("music", music);
        }
        else if(message instanceof NewsMsg) {
            NewsMsg msg = (NewsMsg) message;
            params.put("msgtype", "news");
            Map<String, Object> news = new HashMap<String, Object>();
            List<Object> articles = new ArrayList<Object>();
            List<Article> list = msg.getArticles();
            for(Article article : list) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", article.getTitle());
                map.put("description", article.getDescription());
                map.put("url", article.getUrl());
                map.put("picurl", article.getPicUrl());
                articles.add(map);
            }
            news.put("articles", articles);
            params.put("news", news);
        }
        executePost(url, JSONUtil.toJson(params));
    }

}
