package com.jungle.mix.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant date;

	private Integer homeClubWinProbability;
	private Integer awayClubWinProbability;

	public Match() {
	}

	public Match(Long id, Club homeClub, Club awayClub, Competition competition, Instant date,
			Integer homeClubWinProbability, Integer awayClubWinProbability) {
		this.id = id;
		this.homeClub = homeClub;
		this.awayClub = awayClub;
		this.competition = competition;
		this.date = date;
		this.homeClubWinProbability = homeClubWinProbability;
		this.awayClubWinProbability = awayClubWinProbability;
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

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Integer getHomeClubWinProbability() {
		return homeClubWinProbability;
	}

	public void setHomeClubWinProbability(Integer homeClubWinProbability) {
		this.homeClubWinProbability = homeClubWinProbability;
	}

	public Integer getAwayClubWinProbability() {
		return awayClubWinProbability;
	}

	public void setAwayClubWinProbability(Integer awayClubWinProbability) {
		this.awayClubWinProbability = awayClubWinProbability;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		return Objects.equals(id, other.id);
	}

}
