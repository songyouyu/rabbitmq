package com.imooc.rabbitmqspring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				String msg = new String(message.getBody());
				System.err.println("----------消费者 : " + msg);
			}
		});

		return container;
	}

}
