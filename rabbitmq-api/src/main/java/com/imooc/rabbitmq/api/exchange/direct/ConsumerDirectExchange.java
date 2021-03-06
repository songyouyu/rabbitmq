package com.imooc.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author youyu.song
 * @date 2019/7/24 16:35
 */
public class ConsumerDirectExchange {

	public static void main(String[] args) throws Exception {
		// 创建 ConnectionFactory.
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.28.133");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setAutomaticRecoveryEnabled(true);
		connectionFactory.setNetworkRecoveryInterval(3000);

		// 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		// 通过 connection 创建 channel
		Channel channel = connection.createChannel();

		// 声明
		String exchangeName = "test_direct_exchange";
		String exchangeType = "direct";
		String queueName = "test_direct_queue";
		String routingKey = "test.direct";

		// 声明交换机
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		// 声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 建立绑定关系
		channel.queueBind(queueName, exchangeName, routingKey);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("收到消息 : " + msg);
		}
	}

}
