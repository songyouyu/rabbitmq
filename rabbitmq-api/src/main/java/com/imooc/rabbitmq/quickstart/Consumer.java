package com.imooc.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * @author youyu.song
 * @date 2019/7/24 14:50
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

		// 声明(创建)一个队列
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		// 创建消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

		// 设置 Channel
		channel.basicConsume(queueName, true, queueingConsumer);

		// 获取消息
		while (true) {
			Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("消费消息 : " + msg);
		}

	}

}
