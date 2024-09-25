package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.LeagueDTO;
import com.jungle.mix.entities.League;
import com.jungle.mix.repositories.LeagueRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LeagueService {

	@Autowired
	private LeagueRepository repository;

	@Transactional(readOnly = true)
	public Page<LeagueDTO> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(x -> new LeagueDTO(x));

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

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id))
			throw new ResourceNotFoundException("Resource not found");
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity failure");
		}

	}
}
