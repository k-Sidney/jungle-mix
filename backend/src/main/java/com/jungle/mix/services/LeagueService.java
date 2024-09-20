package com.jungle.mix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.LeagueDTO;
import com.jungle.mix.repositories.LeagueRepository;

@Service
public class LeagueService {

	@Autowired
	private LeagueRepository repository;

	@Transactional(readOnly = true)
	public List<LeagueDTO> findAll() {
		return repository.findAll().stream().map(x -> new LeagueDTO(x)).collect(Collectors.toList());

	}
}
