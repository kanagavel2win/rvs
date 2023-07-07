package com.rvs.springboot.thymeleaf.pojo;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class springtest {

	String db_connect;

	public void setDb_connect(String db) {
		this.db_connect = db;
	}

}
