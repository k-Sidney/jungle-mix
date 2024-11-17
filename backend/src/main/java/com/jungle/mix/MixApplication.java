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

//		// Scrap do Brasileirão
//		ReadAndWrite timesBrasileirao = new ReadAndWrite();
//
//		// Caminho do arquivo
//		String filePath = MixApplication.class.getClassLoader().getResource("TimesBrasileirao.txt").getPath();
//		timesBrasileirao.read(filePath);
//
//		// Impressão dos resultados
//		ArrayList<String> timesENumeros = timesBrasileirao.getTimesENumeros();
//
//		// Loop para scrap dos times no arquivo
//		for (String timeNumero : timesENumeros) {
//			scrapClub.scrap("https://www.sofascore.com/team/football/" + timeNumero);
//			if (scrapClub.getSummary() != null) {
//				timesBrasileirao.write(scrapClub.getSummary());
//			}
//		}
//
//		// Enquanto houver erros no scrap, tentar novamente
//		while (scrapClub.getErrors().size() > 0) {
//			for (int i = 0; i < scrapClub.getErrors().size(); i++) {
//				String erroURL = scrapClub.getErrors().get(i);
//				System.out.println("Erro encontrado: " + erroURL);
//
//				// Tentar novamente o scrap
//				scrapClub.scrap(erroURL);
//				if (scrapClub.getSummary() != null) {
//					timesBrasileirao.write(scrapClub.getSummary());
//				}
//
//				// Se o scrap for bem-sucedido, remover da lista de erros
//				if (scrapClub.getSuccess()) {
//					scrapClub.getErrors().remove(i);
//					System.out.println("Erro removido após sucesso: " + erroURL);
//				}
//			}
//		}

		// Perguntar se o usuário deseja iniciar o scrap
		System.out.println("Iniciar o Scrap? (y para sim)");
		String answer = sc.next();

		while (!answer.equalsIgnoreCase("y")) {
			System.out.println("Responda novamente com 'y' se quiser iniciar o Scrap");
			answer = sc.next();
			System.out.println(answer);
		}

		// Exemplo para ver o scrap de um time específico
		scrapClub.scrap("https://www.sofascore.com/pt/time/futebol/athletico/1967");
	}
}
