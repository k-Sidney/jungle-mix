package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jungle.mix.entities.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

}
