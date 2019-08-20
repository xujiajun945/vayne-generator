package com.dabanjia.vayne.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author xujiajun
 * @date 2019/1/18
 */
@Setter
@Getter
@ToString
public class User {

	private Long id;

	private String phone;

	private String username;

	private Date createdTime;

	private Boolean deleted;
}
