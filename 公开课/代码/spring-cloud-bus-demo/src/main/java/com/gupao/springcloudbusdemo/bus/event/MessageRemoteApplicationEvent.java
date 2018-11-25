package com.gupao.springcloudbusdemo.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 自定义 - 消息 远程应用事件
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/24
 */
public class MessageRemoteApplicationEvent extends RemoteApplicationEvent {

    private MessageRemoteApplicationEvent() {
        // 为了 JSON 序列化
    }

    public MessageRemoteApplicationEvent(String message, String originService,
                                         String destinationService) {
        super(message, originService, destinationService);
    }

    public String getMessage() {
        return (String) getSource();
    }


}
