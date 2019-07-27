package com.imooc.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author youyu.song
 * @date 2019/7/22 21:29
 */
public class Procuder {

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

		// 发送数据
		for (int i = 0; i < 10; i ++) {
			String msg = "Hello, Rabbitmq";
			channel.basicPublish("", "test001", null, msg.getBytes());
		}

		// 关闭连接
		channel.close();
		connection.close();
	}

}
