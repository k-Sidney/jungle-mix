package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.ClubDTO;
import com.jungle.mix.entities.Club;
import com.jungle.mix.repositories.ClubRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubService {

	@Autowired
	private ClubRepository repository;

	@Transactional(readOnly = true)
	public Page<ClubDTO> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(x -> new ClubDTO(x));

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
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClubDTO(entity);
	}

	@Transactional
	public ClubDTO update(Long id, ClubDTO dto) {
		try {
			Club entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
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
}
