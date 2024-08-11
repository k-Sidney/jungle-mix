package com.jungle.mix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jungle.mix.services.Scrap;

@RestController
@RequestMapping("/data")
public class DataController {
	@Autowired
	private Scrap dataService;

	@GetMapping("/fetch")
	public String fetchData() {
		dataService.fetchDataAndSave();
		return "Data fetched and saved!";

	}
}
