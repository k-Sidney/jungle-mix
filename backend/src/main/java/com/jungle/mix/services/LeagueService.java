package com.jungle.mix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jungle.mix.entities.League;
import com.jungle.mix.repositories.LeagueRepository;

@Service
public class LeagueService {
	
	@Autowired
    private	LeagueRepository repository;

	public List<League> findAll(){
		return repository.findAll();
	}
}
