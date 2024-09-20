package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jungle.mix.entities.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
