package com.imooc.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import java.util.HashMap;
import java.util.Map;

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

		// 普通配置
		String exchangeName = "test_dlx_exchange";
		String routingKey = "dlx.#";
		String queueName = "test_dlx_queue";

		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		Map<String, Object> arguments = new HashMap<>(1);
		arguments.put("x-dead-letter-exchange", "dlx.exchange");
		channel.queueDeclare(queueName, true, false, false, arguments);
		channel.queueBind(queueName, exchangeName, routingKey);

		// 死信队列的声明
		channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
		channel.queueDeclare("dlx.queue", true, false, false, null);
		channel.queueBind("dlx.queue", "dlx.exchange", "#");

		channel.basicConsume(queueName, true, new MyConsumer(channel));
	}

}
