package com.imooc.rabbitmq.stream;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author youyu.song
 * @date 2019/8/19 17:02
 */
@EnableBinding(Barista.class)
@Service
public class RabbitmqReceiver {

	@StreamListener(Barista.INPUT_CHANNEL)
	public void receiver(Message message) throws Exception {
		Channel channel = (com.rabbitmq.client.Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		System.out.println("Input Stream 接收数据 : " + message);
		channel.basicAck(deliveryTag, false);
	}
}
