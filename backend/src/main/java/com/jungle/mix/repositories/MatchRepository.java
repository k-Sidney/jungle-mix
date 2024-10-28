package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jungle.mix.entities.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

}
