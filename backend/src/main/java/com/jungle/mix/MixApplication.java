package com.jungle.mix;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jungle.mix.scraper.Scrap;

@SpringBootApplication
public class MixApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MixApplication.class, args);

		Scanner sc = new Scanner(System.in);

		Scrap scrapClub = new Scrap();

		/*
		 * // Scrap do Brasileirão ReadAndWrite timesBrasileirao = new ReadAndWrite();
		 *
		 * String filePath =
		 * MixApplication.class.getClassLoader().getResource("TimesBrasileirao.txt").
		 * getPath(); timesBrasileirao.read(filePath);
		 *
		 * // Impressão dos resultados ArrayList<String> timesENumeros =
		 * timesBrasileirao.getTimesENumeros(); for (String timeNumero : timesENumeros)
		 * { scrapClub.scrap("https://www.sofascore.com/team/football/" + timeNumero);
		 * if (scrapClub.getSummary() != null)
		 * timesBrasileirao.write(scrapClub.getSummary()); }
		 *
		 * while (scrapClub.getErrors().size() > 0) { for (int i = 0; i <
		 * scrapClub.getErrors().size(); i++) {
		 * System.out.println(scrapClub.getErrors().get(i));
		 * scrapClub.scrap(scrapClub.getErrors().get(i));
		 * timesBrasileirao.write(scrapClub.getSummary()); if (scrapClub.getSuccess()) {
		 * scrapClub.getErrors().remove(i); System.out.println("Removido!"); } } }
		 *
		 */

		System.out.println("Iniciar o Scrap? (y para sim)");
		String answer = sc.next();
		while (answer != "y" && answer != "Y") {
			System.out.println("Responda novamente com y se quiser iniciar o Scrap");
			answer = sc.next();
		}
		// Exemplo para ver um time apenas
		scrapClub.scrap("https://www.sofascore.com/team/football/vitoria/1962");
	}
}
