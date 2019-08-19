package com.imooc.rabbitmq.entity;

import java.io.Serializable;

/**
 * @author youyu.song
 * @date 2019/8/19 9:09
 */
public class Order implements Serializable {

	private String id;
	private String name;

	public Order() {
	}

	public Order(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
