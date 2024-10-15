package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.ClubDTO;
import com.jungle.mix.dto.CompetitionDTO;
import com.jungle.mix.entities.Club;
import com.jungle.mix.entities.Competition;
import com.jungle.mix.repositories.ClubRepository;
import com.jungle.mix.repositories.CompetitionRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubService {

	@Autowired
	private ClubRepository repository;

	@Autowired
	private CompetitionRepository competitionRepository;

	@Transactional(readOnly = true)
	public Page<ClubDTO> findAllPaged(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ClubDTO(x));

	}

	@Transactional(readOnly = true)
	public ClubDTO findById(Long id) {
		Optional<Club> obj = repository.findById(id);
		Club entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClubDTO(entity, entity.getCompetitions());
	}

	@Transactional
	public ClubDTO insert(ClubDTO dto) {
		Club entity = new Club();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClubDTO(entity);
	}

	@Transactional
	public ClubDTO update(Long id, ClubDTO dto) {
		try {
			Club entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClubDTO(entity);
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

	private void copyDtoToEntity(ClubDTO dto, Club entity) {
		entity.setName(dto.getName());
		entity.getCompetitions().clear();
		for (CompetitionDTO compDto : dto.getCompetitions()) {
			Competition competition = competitionRepository.getReferenceById(compDto.getId());
			entity.getCompetitions().add(competition);
		}
	}
}
