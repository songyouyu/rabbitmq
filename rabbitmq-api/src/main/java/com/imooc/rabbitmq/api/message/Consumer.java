package com.imooc.rabbitmq.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Map;

/**
 * @author youyu.song
 * @date 2019/7/27 10:53
 */
public class Consumer {

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

		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("收到消息 : " + msg);
			Map<String, Object> map = delivery.getProperties().getHeaders();
			System.out.println(map.get("hello1"));
			System.out.println(map.get("hello2"));
		}
	}

}
