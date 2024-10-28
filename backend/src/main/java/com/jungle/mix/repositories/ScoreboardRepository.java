package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jungle.mix.entities.Scoreboard;

@Repository
public interface ScoreboardRepository extends JpaRepository<Scoreboard, Long> {

}
