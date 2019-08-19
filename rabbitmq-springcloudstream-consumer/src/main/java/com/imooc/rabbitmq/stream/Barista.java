package com.imooc.rabbitmq.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author youyu.song
 * @date 2019/8/19 17:01
 */
public interface Barista {

	String INPUT_CHANNEL = "input_channel";

	@Input(Barista.INPUT_CHANNEL)
	SubscribableChannel loginput();

}
