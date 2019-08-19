package com.imooc.rabbitmq.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author youyu.song
 * @date 2019/8/19 15:11
 */
@EnableBinding(Barista.class)
@Service
public class RabbitmqSender {

	@Autowired
	private Barista barista;

	public String sendMessage(Object message, Map<String, Object> properties) throws Exception {
		MessageHeaders headers = new MessageHeaders(properties);
		Message msg = MessageBuilder.createMessage(message, headers);
		boolean send = barista.logoutput().send(msg);
		System.out.println("发送数据 ：" + message + ", 是否成功 : " + send);

		return null;
	}

}
