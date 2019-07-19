package com.hoang.lsp.core.callback;

import org.springframework.amqp.rabbit.core.ChannelCallback;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

public class MessageCountChannelCallback implements ChannelCallback<DeclareOk> {

    private final String reprocessorQueue;

    public MessageCountChannelCallback(String reprocessorQueue) {
        this.reprocessorQueue = reprocessorQueue;
    }

    @Override
    public DeclareOk doInRabbit(Channel channel) throws Exception {
        return channel.queueDeclarePassive(this.reprocessorQueue);
    }

}
