package com.jungle.mix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.CompetitionDTO;
import com.jungle.mix.entities.Competition;
import com.jungle.mix.repositories.CompetitionRepository;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CompetitionService {

	@Autowired
	private CompetitionRepository repository;

	@Transactional(readOnly = true)
	public Page<CompetitionDTO> findAllPaged(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new CompetitionDTO(x));

	}

	@Transactional(readOnly = true)
	public CompetitionDTO findById(Long id) {
		Optional<Competition> obj = repository.findById(id);
		return new CompetitionDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
	}

	@Transactional
	public CompetitionDTO insert(CompetitionDTO dto) {
		Competition entity = new Competition();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CompetitionDTO(entity);
	}

	@Transactional
	public CompetitionDTO update(Long id, CompetitionDTO dto) {
		try {
			Competition entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CompetitionDTO(entity);
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
