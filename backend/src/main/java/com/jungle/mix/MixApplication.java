package com.jungle.mix;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jungle.mix.services.ReadClubs;
import com.jungle.mix.services.Scrap;

@SpringBootApplication
public class MixApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MixApplication.class, args);

		Scrap scrapClub = new Scrap();

		// Scrap do Brasileirão
		ReadClubs timesBrasileirao = new ReadClubs();
		String filePath = "C:\\Users\\Kleuber\\Desktop\\TimesBrasileirao.txt";
		timesBrasileirao.lerArquivo(filePath);

		// Impressão dos resultados
		ArrayList<String> timesENumeros = timesBrasileirao.getTimesENumeros();
		for (String timeNumero : timesENumeros) {
			scrapClub.scrap("https://www.sofascore.com/team/football/" + timeNumero);
		}
		// Exemplo para ver um time apenas
		// scrapClub.scrap("https://www.sofascore.com/team/football/red-bull-bragantino/1999");
	}
}