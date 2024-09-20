package com.jungle.mix.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jungle.mix.entities.League;
import com.jungle.mix.services.LeagueService;

@RestController
@RequestMapping(value = "/leagues")
public class LeagueResource {

	@Autowired
	private LeagueService service;

	@GetMapping
	public ResponseEntity<List<League>> findAll() {
		List<League> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

}
