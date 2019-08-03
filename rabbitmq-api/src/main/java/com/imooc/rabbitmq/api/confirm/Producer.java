package com.imooc.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author youyu.song
 * @date 2019/8/3 18:01
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.28.133");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		Connection connection = connectionFactory.newConnection();

		Channel channel = connection.createChannel();

		// 指定消息的投递模式：消息确认模式
		channel.confirmSelect();

		String exchangeName = "test_confirm_exchange";
		String routingKey = "confirm.save";

		String msg = "hello rabbitmq confirm";
		channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

		// 此时连接不能关闭，因为要进行消息的监听
		// 添加一个确认监听
		channel.addConfirmListener(new ConfirmListener() {
			// deliveryTag:消息的唯一标签
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("no response");
			}

			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("response");
			}
		});

	}

}
