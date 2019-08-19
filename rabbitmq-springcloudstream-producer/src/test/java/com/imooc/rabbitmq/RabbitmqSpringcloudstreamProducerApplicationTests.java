package com.imooc.rabbitmq;

import com.imooc.rabbitmq.stream.RabbitmqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringcloudstreamProducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RabbitmqSender rabbitmqSender;

	@Test
	public void sendMessageTest() {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put("time", LocalTime.now());
			rabbitmqSender.sendMessage("Hello, rabbitmq stream", properties);
		} catch (Exception e) {
			System.out.println("--------error-------");
			e.printStackTrace();
		}
	}

}
