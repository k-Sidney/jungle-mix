package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.ScoreboardDTO;
import com.jungle.mix.entities.Scoreboard;
import com.jungle.mix.repositories.ScoreboardRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ScoreboardService {

	@Autowired
	private ScoreboardRepository repository;

	@Transactional(readOnly = true)
	public Page<ScoreboardDTO> findAllPaged(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ScoreboardDTO(x));
	}

	@Transactional(readOnly = true)
	public ScoreboardDTO findById(Long Id) {
		Optional<Scoreboard> obj = repository.findById(Id);
		return new ScoreboardDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
	}

	@Transactional
	public ScoreboardDTO insert(ScoreboardDTO dto) {
		Scoreboard entity = new Scoreboard();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ScoreboardDTO(entity);
	}

	@Transactional
	public ScoreboardDTO update(Long id, ScoreboardDTO dto) {
		try {
			Scoreboard entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ScoreboardDTO(entity);
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

	private void copyDtoToEntity(ScoreboardDTO dto, Scoreboard entity) {
		entity.setAwayScore(dto.getAwayScore());
		entity.setHomeScore(dto.getHomeScore());
	}
}
