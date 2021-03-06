package com.imooc.rabbitmq.api.limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author youyu.song
 * @date 2019/8/4 15:33
 */
public class MyConsumer extends DefaultConsumer {

	private Channel channel;

	public MyConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
							   AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.out.println("-----------consume message----------");
		System.out.println("consumerTag: " + consumerTag);
		System.out.println("envelope: " + envelope);
		System.out.println("properties: " + properties);
		System.out.println("body: " + new String(body));

		// multiple:设置为false，不批量接收.
		channel.basicAck(envelope.getDeliveryTag(), false);
	}
}
