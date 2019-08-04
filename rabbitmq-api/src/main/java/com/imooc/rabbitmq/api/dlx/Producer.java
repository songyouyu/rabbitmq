package com.imooc.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
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

		String exchangeName = "test_dlx_exchange";
		String routingKey = "dlx.save";

		String msg = "Hello rabbitmq dlx";

		for(int i =0; i<1; i ++) {
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();
			channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
		}
	}

}
