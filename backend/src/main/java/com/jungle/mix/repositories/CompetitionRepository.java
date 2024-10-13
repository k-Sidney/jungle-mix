package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jungle.mix.entities.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}
