package com.imooc.rabbitmq.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

		String exchangeName = "test_ack_exchange";
		String routingKey = "ack.save";

		for (int i = 0; i < 5; i ++) {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("num", i);

			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.headers(headers)
					.build();

			String msg = "Hello rabbitmq qos" + i;
			channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
		}
	}

}
