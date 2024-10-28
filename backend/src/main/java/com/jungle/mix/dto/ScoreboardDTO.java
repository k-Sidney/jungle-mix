package com.jungle.mix.dto;

import java.io.Serializable;

import com.jungle.mix.entities.Scoreboard;

public class ScoreboardDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer homeScore;
	private Integer awayScore;

	public ScoreboardDTO() {
	}

	public ScoreboardDTO(Long id, Integer homeScore, Integer awayScore) {
		this.id = id;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
	}

	public ScoreboardDTO(Scoreboard entity) {
		this.id = entity.getId();
		this.homeScore = entity.getHomeScore();
		this.awayScore = entity.getAwayScore();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer awayScore) {
		this.awayScore = awayScore;
	}

}
