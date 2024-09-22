package com.jungle.mix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.LeagueDTO;
import com.jungle.mix.entities.League;
import com.jungle.mix.repositories.LeagueRepository;
import com.jungle.mix.services.exceptions.EntityNotFoundException;

@Service
public class LeagueService {

	@Autowired
	private LeagueRepository repository;

	@Transactional(readOnly = true)
	public List<LeagueDTO> findAll() {
		return repository.findAll().stream().map(x -> new LeagueDTO(x)).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public LeagueDTO findById(Long id) {
		Optional<League> obj = repository.findById(id);
		return new LeagueDTO(obj.orElseThrow(() -> new EntityNotFoundException("Entity not found")));
	}
}
