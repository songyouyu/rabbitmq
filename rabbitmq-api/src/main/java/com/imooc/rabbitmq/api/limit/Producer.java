package com.imooc.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

		String exchangeName = "test_qos_exchange";
		String routingKey = "qos.save";

		String msg = "Hello rabbitmq qos";

		for (int i = 0; i < 5; i ++) {
			channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
		}
	}

}
