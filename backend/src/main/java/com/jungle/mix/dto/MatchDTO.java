package com.jungle.mix.dto;

import java.io.Serializable;
import java.time.Instant;

import com.jungle.mix.entities.Match;

public class MatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private ClubDTO homeClub;
	private ClubDTO awayClub;
	private CompetitionDTO competition;
	private Instant date;
	private Integer homeClubWinProbability;
	private Integer awayClubWinProbability;

	public MatchDTO() {
	}

	public MatchDTO(Match entity) {
		this.id = entity.getId();
		this.homeClub = new ClubDTO(entity.getHomeClub());
		this.awayClub = new ClubDTO(entity.getAwayClub());
		this.competition = new CompetitionDTO(entity.getCompetition());
		this.date = entity.getDate();
		this.homeClubWinProbability = entity.getHomeClubWinProbability();
		this.awayClubWinProbability = entity.getAwayClubWinProbability();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClubDTO getHomeClub() {
		return homeClub;
	}

	public void setHomeClub(ClubDTO homeClub) {
		this.homeClub = homeClub;
	}

	public ClubDTO getAwayClub() {
		return awayClub;
	}

	public void setAwayClub(ClubDTO awayClub) {
		this.awayClub = awayClub;
	}

	public CompetitionDTO getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionDTO competition) {
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

}
