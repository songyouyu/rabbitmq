package com.imooc.rabbitmq.producer;

import com.imooc.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author youyu.song
 * @date 2019/8/18 17:24
 */
@Component
public class RabbitSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送消息方法调用, 构建 message 消息.
	 * @param message
	 * @param properties
	 * @throws Exception
	 */
	public void send(Object message, Map<String, Object> properties) throws Exception {
		MessageHeaders messageHeaders = new MessageHeaders(properties);
		Message msg = MessageBuilder.createMessage(message, messageHeaders);
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
	}

	/**
	 * 发送消息方法调用, 构建自定义对象消息
	 * @param order
	 * @throws Exception
	 */
	public void sendOrder(Order order) throws Exception {
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend("exchange-2", "springboot.def", order, correlationData);
	}

	/**
	 * confirm 确认回调函数
	 */
	final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.err.println("correlationData : " + correlationData);
			System.err.println("ack : " + ack);
			if (! ack) {
				System.err.println("进行异常处理逻辑...");
			}
		}
	};

	/**
	 * return 返回回调函数
	 */
	final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
			System.err.println("return exchange: " + exchange + ", routingKey: "
					+ routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
		}
	};

}
