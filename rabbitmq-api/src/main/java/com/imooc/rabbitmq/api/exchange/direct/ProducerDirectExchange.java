package com.imooc.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author youyu.song
 * @date 2019/7/24 16:31
 */
public class ProducerDirectExchange {

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
		String exchangeName = "test_direct_exchange";
		String routingKey = "test.direct";

		// 发送
		String msg = "Hello world Direct Exchange";
		channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

		channel.close();
		connection.close();
	}

}
