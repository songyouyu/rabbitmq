package com.imooc.rabbitmq.api.ack;

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
		System.err.println("body: " + new String(body));

		if((Integer)properties.getHeaders().get("num") == 0) {
			channel.basicNack(envelope.getDeliveryTag(), false, true);
		} else {
			channel.basicAck(envelope.getDeliveryTag(), false);
		}
	}
}
