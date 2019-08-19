package com.imooc.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author youyu.song
 * @date 2019/8/19 10:07
 */
@Component
public class RabbitReceiver {

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1", durable = "true"),
			exchange = @Exchange(value = "exchange-1", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
	))
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.err.println("消费端接收消息 : " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		// 手工 ack.
		channel.basicAck(deliveryTag, false);
	}


	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
					durable="${spring.rabbitmq.listener.order.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
					durable="${spring.rabbitmq.listener.order.exchange.durable}",
					type= "${spring.rabbitmq.listener.order.exchange.type}",
					ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.order.key}"
	))
	@RabbitHandler
	public void onOrderMessage(@Payload com.imooc.rabbitmq.entity.Order order,
							   @Headers Map<String, Object> headers,
							   Channel channel) throws Exception {
		System.err.println("消费端 order : " + order.getId());
		Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		// 手工 ack.
		channel.basicAck(deliveryTag, false);

	}

}
