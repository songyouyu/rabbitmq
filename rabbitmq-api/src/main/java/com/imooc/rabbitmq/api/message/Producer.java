package com.imooc.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyu.song
 * @date 2019/7/27 10:48
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		// 创建 ConnectionFactory.
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.28.133");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		// 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		// 通过 connection 创建 channel
		Channel channel = connection.createChannel();

		Map<String, Object> headers = new HashMap<>();
		headers.put("hello1", "world1");
		headers.put("hello2", "world2");

		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				.deliveryMode(2)
				.contentEncoding("UTF-8")
				.expiration("10000")
				.headers(headers)
				.build();

		String msg = "Hello World";
		channel.basicPublish("", "test001", properties, msg.getBytes());

		channel.close();
		connection.close();
	}

}
