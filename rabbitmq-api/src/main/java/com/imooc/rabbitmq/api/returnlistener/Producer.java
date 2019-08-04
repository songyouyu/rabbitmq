package com.imooc.rabbitmq.api.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;

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

		String exchangeName = "test_return_exchange";
		String routingKey = "return.save";
		String routingKeyError = "abc.save";

		String msg = "Hello rabbitmq return listener";

		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange,
									 String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.err.println("replyCode: " + replyCode);
				System.err.println("replyText: " + replyText);
				System.err.println("exchange: " + exchange);
				System.err.println("routingKey: " + routingKey);
				System.err.println("properties: " + properties);
				System.err.println("body: " + new String(body));
			}
		});

		// mandatory设置为true.
		channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
		channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
	}

}
