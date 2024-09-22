package com.jungle.mix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jungle.mix.dto.LeagueDTO;
import com.jungle.mix.services.LeagueService;

@RestController
@RequestMapping(value = "/leagues")
public class LeagueController {

	@Autowired
	private LeagueService service;

	@GetMapping
	public ResponseEntity<List<LeagueDTO>> findAll() {
		List<LeagueDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<LeagueDTO> findById(@PathVariable Long id) {
		LeagueDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

}
