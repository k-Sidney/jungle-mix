package com.jungle.mix.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jungle.mix.dto.MatchDTO;
import com.jungle.mix.entities.Club;
import com.jungle.mix.entities.Competition;
import com.jungle.mix.entities.Match;
import com.jungle.mix.repositories.ClubRepository;
import com.jungle.mix.repositories.MatchRepository;
import com.jungle.mix.scraper.Scrap;
import com.jungle.mix.services.exceptions.DatabaseException;
import com.jungle.mix.services.exceptions.ResourceNotFoundException;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MatchService {

	@Autowired
	private MatchRepository repository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private Scrap scrap;

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
		System.out.println("Saving match with home club: " + entity.getHomeClub().getId());
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

	@Transactional
	public MatchDTO matchCreation() {
		try {
			checkInjection();

			// Realiza o scraping das informações
			scrap.scrap("https://www.sofascore.com/pt/time/futebol/ponte-preta/1969");
			System.out.println("Nome do time de casa: " + scrap.getHomeTeamName());
			System.out.println("Nome do time de fora: " + scrap.getAwayTeamName());

			// Recupera os clubes com base nos nomes fornecidos
			Optional<Club> homeClubOptional = clubRepository.findByName(scrap.getHomeTeamName());
			Club homeClub = homeClubOptional.orElseThrow(
					() -> new IllegalArgumentException("Clube não encontrado: " + scrap.getHomeTeamName()));
			Optional<Club> awayClubOptional = clubRepository.findByName(scrap.getAwayTeamName());
			Club awayClub = awayClubOptional.orElseThrow(
					() -> new IllegalArgumentException("Clube não encontrado: " + scrap.getAwayTeamName()));

			Competition competition = competitionService.findClosestCompetitionByName(scrap.getCompetitionName())
					.orElseThrow(() -> new IllegalArgumentException(
							"Competição não encontrada: " + scrap.getCompetitionName()));

			// Converte as odds para valores inteiros
			int homeOddInt, awayOddInt;
			try {
				homeOddInt = (int) Math.round(Double.parseDouble(scrap.getHomeOdd().replace("%", "")));
				awayOddInt = (int) Math.round(Double.parseDouble(scrap.getAwayOdd().replace("%", "")));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Erro ao converter odd para número: " + scrap.getHomeOdd() + " ou " + scrap.getAwayOdd());
			}

			// Processa a data recebida no formato "16/11/202412:00"
			Instant matchDate;
			try {
				String scrapDate = scrap.getDate(); // Data no formato recebido do Scrap
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm")
						.withZone(ZoneId.systemDefault());
				matchDate = Instant.from(formatter.parse(scrapDate));
			} catch (Exception e) {
				throw new IllegalArgumentException("Erro ao processar a data: " + scrap.getDate(), e);
			}

			// Cria e configura a entidade Match
			Match match = new Match();
			match.setHomeClub(homeClub);
			match.setAwayClub(awayClub);
			match.setCompetition(competition);
			match.setDate(matchDate); // Define a data convertida
			match.setHomeClubWinProbability(homeOddInt);
			match.setAwayClubWinProbability(awayOddInt);

			// Salva a partida no banco de dados
			match = repository.save(match);
			if (match.getId() == null) {
				throw new DatabaseException("Erro ao salvar a partida no banco de dados");
			}

			System.out.println("Partida salva com sucesso: " + match);
			return new MatchDTO(match);

		} catch (IllegalArgumentException | DatabaseException e) {
			// Tratamento de exceções customizadas
			System.err.println("Erro ao criar partida: " + e.getMessage());
			throw e;
		}
	}

	@PostConstruct
	public void checkInjection() {
		if (clubRepository == null) {
			System.out.println("clubRepository is null!");
		} else {
			System.out.println("clubRepository injected successfully!");
		}
	}

	private void copyDtoToEntity(MatchDTO dto, Match entity) {

		Club homeClub = new Club();
		homeClub.setId(dto.getHomeClub().getId());

		Club awayClub = new Club();
		awayClub.setId(dto.getAwayClub().getId());

		Competition competition = new Competition();
		competition.setId(dto.getCompetition().getId());

		entity.setAwayClub(awayClub);
		entity.setHomeClub(homeClub);
		entity.setCompetition(competition);
		entity.setDate(dto.getDate());
		entity.setAwayClubWinProbability(dto.getAwayClubWinProbability());
		entity.setHomeClubWinProbability(dto.getHomeClubWinProbability());
	}

}
