package com.imooc.rabbitmqspring;

import com.imooc.rabbitmqspring.adapter.MessageDelegate;
import com.imooc.rabbitmqspring.convert.ImageMessageConverter;
import com.imooc.rabbitmqspring.convert.PDFMessageConverter;
import com.imooc.rabbitmqspring.convert.TextMessageConverter;
import com.imooc.rabbitmqspring.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author youyu.song
 * @date 2019/8/10 16:06
 */
@Configuration
@ComponentScan({"com.imooc.rabbitmqspring"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("192.168.28.133:5672");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setVirtualHost("/");

		return connectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);

		return rabbitAdmin;
	}

	@Bean
	public TopicExchange exchange001() {
		return new TopicExchange("topic001", true, false);
	}

	@Bean
	public Queue queue001() {
		// 队列持久
		return new Queue("queue001", true);
	}

	@Bean
	public Binding binding001() {
		return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
	}

	@Bean
	public TopicExchange exchange002() {
		return new TopicExchange("topic002", true, false);
	}

	@Bean
	public Queue queue002() {
		return new Queue("queue002", true);
	}

	@Bean
	public Binding binding002() {
		return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
	}

	@Bean
	public Queue queue003() {
		return new Queue("queue003", true);
	}

	@Bean
	public Binding binding003() {
		return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
	}

	@Bean
	public Queue queue_image() {
		return new Queue("image_queue", true);
	}

	@Bean
	public Queue queue_pdf() {
		return new Queue("pdf_queue", true);
	}

	/**
	 * 消息模版组件
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		return rabbitTemplate;
	}

	/**
	 * 消息监听容器
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
		// 当前的消费者数量
		container.setConcurrentConsumers(1);
		container.setMaxConcurrentConsumers(5);
		container.setDefaultRequeueRejected(false);
		// 自动签收
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setExposeListenerChannel(true);
		// 消费端的标签策略
		container.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return queue + "_" + UUID.randomUUID().toString();
			}
		});

		//container.setMessageListener(new ChannelAwareMessageListener() {
		//	@Override
		//	public void onMessage(Message message, Channel channel) throws Exception {
		//		String msg = new String(message.getBody());
		//		System.err.println("----------消费者 : " + msg);
		//	}
		//});


		/*
		// 消息适配器
		// 适配器方式, 默认是有自己的方法名字的：handleMessage
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		// 修改默认的方法名, 指定自定义的方法名
		adapter.setDefaultListenerMethod("consumeMessage");
		// 添加转换器, 从字节数组转换为 String.
		adapter.setMessageConverter(new TextMessageConverter());
		container.setMessageListener(adapter);
		 */


		/*
		// 适配器方式，方法名称和队列名称也可以进行一一匹配
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		adapter.setMessageConverter(new TextMessageConverter());

		Map<String, String> queueOrTagToMethodName = new HashMap<>();
		queueOrTagToMethodName.put("queue001", "method1");
		queueOrTagToMethodName.put("queue002", "method2");

		adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
		 */


		/*
		// 支持 json 格式的转换器.
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		adapter.setDefaultListenerMethod("consumeMessage");

		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		adapter.setMessageConverter(jackson2JsonMessageConverter);

		container.setMessageListener(adapter);
		 */


		/*
		// DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		adapter.setDefaultListenerMethod("consumeMessage");

		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
		jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

		adapter.setMessageConverter(jackson2JsonMessageConverter);
		container.setMessageListener(adapter);
		 */


		/*
		// DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
		MessageListenerAdapter adapter = new MessageListenerAdapter();
		adapter.setDefaultListenerMethod("consumeMessage");

		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

		Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
		idClassMapping.put("order", com.imooc.rabbitmqspring.entity.Order.class);
		idClassMapping.put("packaged", com.imooc.rabbitmqspring.entity.Packaged.class);

		javaTypeMapper.setIdClassMapping(idClassMapping);
		jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

		adapter.setMessageConverter(jackson2JsonMessageConverter);
		container.setMessageListener(adapter);
		 */


		// ext convert
		MessageListenerAdapter adapter = new MessageListenerAdapter();
		adapter.setDefaultListenerMethod("consumeMessage");

		// 全局的转换器
		ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();

		TextMessageConverter textConvert = new TextMessageConverter();
		converter.addDelegate("text", textConvert);
		converter.addDelegate("html/text", textConvert);
		converter.addDelegate("xml/text", textConvert);
		converter.addDelegate("text/plain", textConvert);

		Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
		converter.addDelegate("json", jsonConvert);
		converter.addDelegate("application/json", jsonConvert);

		ImageMessageConverter imageConverter = new ImageMessageConverter();
		converter.addDelegate("image/png", imageConverter);
		converter.addDelegate("image", imageConverter);

		PDFMessageConverter pdfConverter = new PDFMessageConverter();
		converter.addDelegate("application/pdf", pdfConverter);

		adapter.setMessageConverter(converter);
		container.setMessageListener(adapter);

		return container;
	}

}
