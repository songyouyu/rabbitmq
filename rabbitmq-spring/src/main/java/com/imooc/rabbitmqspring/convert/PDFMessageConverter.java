package com.imooc.rabbitmqspring.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author youyu.song
 * @date 2019/8/18 16:44
 */
public class PDFMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
		throw new MessageConversionException("convert error !");
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		System.err.println("-----------PDF MessageConverter----------");

		byte[] body = message.getBody();
		String fileName = UUID.randomUUID().toString();
		String path = "g:/test/" + fileName + ".pdf";
		File file = new File(path);
		try {
			Files.copy(new ByteArrayInputStream(body), file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
