package com.imooc.rabbitmq;

import com.imooc.rabbitmq.entity.Order;
import com.imooc.rabbitmq.producer.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringbootProducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RabbitSender rabbitSender;

	@Test
	public void testSend1() throws Exception {
		Map<String, Object> properties = new HashMap<>();
		properties.put("number", "123");
		properties.put("sendTime", LocalTime.now());
		rabbitSender.send("hello rabbitmq for springboot", properties);
	}

	@Test
	public void testSend2() throws Exception {
		Order order = new Order("001", "第一个订单");
		rabbitSender.sendOrder(order);
	}

}
