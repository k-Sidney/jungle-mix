package com.jungle.mix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jungle.mix.entities.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
	}

