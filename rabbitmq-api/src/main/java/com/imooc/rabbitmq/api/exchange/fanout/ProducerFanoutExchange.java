package com.imooc.rabbitmq.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author youyu.song
 * @date 2019/7/27 10:41
 */
public class ProducerFanoutExchange {

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

		//声明
		String exchangeName = "test_fanout_exchange";

		String msg = "Hello world Fanout Exchange";
		channel.basicPublish(exchangeName, "", null, msg.getBytes());

		channel.close();
		connection.close();
	}

}
