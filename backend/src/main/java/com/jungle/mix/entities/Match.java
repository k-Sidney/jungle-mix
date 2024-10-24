package com.jungle.mix.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_match")
public class Match implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "home_club_id")
	private Club homeClub;

	@ManyToOne
	@JoinColumn(name = "away_club_id")
	private Club awayClub;

	@ManyToOne
	@JoinColumn(name = "competition_id")
	private Competition competition;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "scoreboard_id", referencedColumnName = "id")
	private Scoreboard scoreboard;

	public Match() {
	}

	public Match(Long id, Club homeClub, Club awayClub, Competition competition, Scoreboard scoreboard) {
		this.id = id;
		this.homeClub = homeClub;
		this.awayClub = awayClub;
		this.competition = competition;
		this.scoreboard = scoreboard;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Club getHomeClub() {
		return homeClub;
	}

	public void setHomeClub(Club homeClub) {
		this.homeClub = homeClub;
	}

	public Club getAwayClub() {
		return awayClub;
	}

	public void setAwayClub(Club awayClub) {
		this.awayClub = awayClub;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

}
