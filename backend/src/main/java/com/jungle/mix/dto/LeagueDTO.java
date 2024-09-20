package com.jungle.mix.dto;

import java.io.Serializable;

import com.jungle.mix.entities.League;

public class LeagueDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;

	public LeagueDTO() {
	}

	public LeagueDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public LeagueDTO(League entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
