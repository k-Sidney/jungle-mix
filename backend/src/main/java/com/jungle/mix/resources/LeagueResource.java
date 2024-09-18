package com.jungle.mix.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jungle.mix.entities.League;

@RestController
@RequestMapping(value = "/leagues")
public class LeagueResource {

	@GetMapping
	public ResponseEntity<List<League>> findAll() {
		List<League> list = new ArrayList<>();
		list.add(new League(1L, "Brasileirao"));
		list.add(new League(2L, "PremierLeague"));
		return ResponseEntity.ok().body(list);
	}

}
