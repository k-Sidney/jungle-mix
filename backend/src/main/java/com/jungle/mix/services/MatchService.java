package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.MatchDTO;
import com.jungle.mix.entities.Match;
import com.jungle.mix.repositories.MatchRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MatchService {

	@Autowired
	private MatchRepository repository;

	@Transactional(readOnly = true)
	public Page<MatchDTO> findAllPaged(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new MatchDTO(x));
	}

	@Transactional(readOnly = true)
	public MatchDTO findById(Long Id) {
		Optional<Match> obj = repository.findById(Id);
		return new MatchDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
	}

	@Transactional
	public MatchDTO insert(MatchDTO dto) {
		Match entity = new Match();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new MatchDTO(entity);
	}

	@Transactional
	public MatchDTO update(Long id, MatchDTO dto) {
		try {
			Match entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new MatchDTO(entity);
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

	private void copyDtoToEntity(MatchDTO dto, Match entity) {
		entity.setAwayClub(dto.getAwayClub());
		entity.setHomeClub(dto.getHomeClub());
		entity.setCompetition(dto.getCompetition());
		entity.setAwayClubWinProbability(dto.getAwayClubWinProbability());
		entity.setHomeClubWinProbability(dto.getHomeClubWinProbability());
		entity.setScoreboard(dto.getScoreBoard());

	}
}
