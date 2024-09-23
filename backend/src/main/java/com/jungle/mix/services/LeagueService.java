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
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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
		return new LeagueDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
	}

	@Transactional
	public LeagueDTO insert(LeagueDTO dto) {
		League entity = new League();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new LeagueDTO(entity);
	}

	@Transactional
	public LeagueDTO update(Long id, LeagueDTO dto) {
		try {
			League entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new LeagueDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
}
