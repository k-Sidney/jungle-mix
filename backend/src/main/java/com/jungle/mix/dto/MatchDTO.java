package com.jungle.mix.dto;

import java.io.Serializable;

import com.jungle.mix.entities.Club;
import com.jungle.mix.entities.Competition;
import com.jungle.mix.entities.Match;
import com.jungle.mix.entities.Scoreboard;

public class MatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Club homeClub;
	private Club awayClub;
	private Competition competition;
	private Scoreboard scoreBoard;
	private Integer homeClubWinProbability;
	private Integer awayClubWinProbability;

	public MatchDTO() {
	}

	public MatchDTO(Long id, Club homeClub, Club awayClub, Competition competition, Scoreboard scoreBoard,
			Integer homeClubWinProbability, Integer awayClubWinProbability) {
		this.id = id;
		this.homeClub = homeClub;
		this.awayClub = awayClub;
		this.competition = competition;
		this.scoreBoard = scoreBoard;
		this.homeClubWinProbability = homeClubWinProbability;
		this.awayClubWinProbability = awayClubWinProbability;
	}

	public MatchDTO(Match entity) {
		this.id = entity.getId();
		this.homeClub = entity.getHomeClub();
		this.awayClub = entity.getAwayClub();
		this.competition = entity.getCompetition();
		this.scoreBoard = entity.getScoreboard();
		this.homeClubWinProbability = entity.getHomeClubWinProbability();
		this.awayClubWinProbability = entity.getAwayClubWinProbability();
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

	public Scoreboard getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(Scoreboard scoreBoard) {
		this.scoreBoard = scoreBoard;
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

}
