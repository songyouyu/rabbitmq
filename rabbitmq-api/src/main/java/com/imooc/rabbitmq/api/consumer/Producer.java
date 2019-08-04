package com.imooc.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author youyu.song
 * @date 2019/8/4 15:03
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.28.133");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		Connection connection = connectionFactory.newConnection();

		Channel channel = connection.createChannel();

		String exchangeName = "test_consumer_exchange";
		String routingKey = "consumer.save";

		String msg = "Hello rabbitmq consumer";

		// mandatory设置为true.
		channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
	}

}
