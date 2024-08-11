package com.jungle.mix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String data;

	public Data() {
	}

	public Data(String data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
