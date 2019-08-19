package com.imooc.rabbitmq.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 定义来作为后面类的参数, 这个接口定义了通道类型和通道名称
 * 通道名称是作为配置用, 通道类型则决定了 app 会使用这一通道进行发送消息还是接收消息.
 * @author youyu.song
 * @date 2019/8/19 14:51
 */
public interface Barista {

	String OUTPUT_CHANNEL = "output_channel";
	// @Output 声明了它是一个输出类型的通道, 名字是 output_channel,
	@Output(Barista.OUTPUT_CHANNEL)
	MessageChannel logoutput();


	//String INPUT_CHANNEL = "input_channel";
	//@Input(Barista.INPUT_CHANNEL)
	//SubscribableChannel loginput();
}
