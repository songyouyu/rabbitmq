package com.imooc.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author youyu.song
 * @date 2019/8/4 15:06
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.28.133");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String exchangeName = "test_qos_exchange";
		String routingKey = "qos.#";
		String queueName = "test_qos_queue";

		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		channel.basicQos(0, 1, false);
		// 限流方式：autoAck 设置为false.
		channel.basicConsume(queueName, false, new MyConsumer(channel));
	}

}
