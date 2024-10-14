package com.jungle.mix.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jungle.mix.entities.Club;
import com.jungle.mix.entities.Competition;

public class ClubDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private List<CompetitionDTO> competitions = new ArrayList<>();

	public ClubDTO() {
	}

	public ClubDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ClubDTO(Club entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public ClubDTO(Club entity, Set<Competition> competitions) {
		this(entity);
		competitions.forEach(comp -> this.competitions.add(new CompetitionDTO(comp)));
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

	public List<CompetitionDTO> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<CompetitionDTO> competitions) {
		this.competitions = competitions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
