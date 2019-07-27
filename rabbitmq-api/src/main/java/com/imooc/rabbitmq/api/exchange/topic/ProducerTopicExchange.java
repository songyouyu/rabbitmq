package com.imooc.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author youyu.song
 * @date 2019/7/27 10:27
 */
public class ProducerTopicExchange {

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

		// 声明
		String exchangeName = "test_topic_exchange";
		String routingKey1 = "user.save";
		String routingKey2 = "user.update";
		String routingKey3 = "user.delete.abc";

		// 发送
		String msg = "Hello world Topic Exchange";
		channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
		channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
		channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());

		channel.close();
		connection.close();
	}

}
