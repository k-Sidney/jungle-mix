package com.jungle.mix.scraper;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

@Component
public class Scrap {

	String summary = null;
	List<String> errors = new ArrayList<>();
	Boolean success = false;
	private String homeTeamName;
	private String awayTeamName;
	private String competitionName;
	private String date;
	private String homeOdd;
	private String awayOdd;

	public String getHomeOdd() {
		return homeOdd;
	}

	public void setHomeOdd(String homeOdd) {
		this.homeOdd = homeOdd;
	}

	public String getAwayOdd() {
		return awayOdd;
	}

	public void setAwayOdd(String awayOdd) {
		this.awayOdd = awayOdd;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void scrap(String url) {

		System.setProperty("webdriver.chrome", "/mix/src/main/resources/chrome.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", null);
		options.addArguments("window-size=1200,750");
		options.addArguments(
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

		WebDriver driver = new ChromeDriver(options);
		driver.get(url);

		String homeTeam = driver.getCurrentUrl();
		String startPattern = "football/";
		int startIndex = homeTeam.indexOf(startPattern);

		startIndex += startPattern.length();
		int endIndex = homeTeam.indexOf('/', startIndex);

		homeTeam = homeTeam.substring(startIndex, endIndex);

		String homeTeamName = driver.findElement(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div/div[2]/div[1]/div[1]/div/div[2]/div/div[1]/div[2]/div[1]/h2"))
				.getText();

		summary = "Não existem estatisitcas para a próxima partida do " + homeTeamName;

		WebElement showMoreBtn = null;
		boolean buttonFound = false;
		int attempts = 0;

		while (!buttonFound && attempts < 16) {
			try {
				showMoreBtn = driver.findElement(By.xpath(
						"//*[@id='__next']/main/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div[2]/div/div[1]/div/div/a/button"));
				buttonFound = true;
			} catch (NoSuchElementException e) {
				attempts++;
				if (attempts == 16) {
					System.out.println("Botão 'Show More' não encontrado após 12 tentativas " + homeTeam);
					summary = null;
					System.out.println(driver.getCurrentUrl());
					errors.add(driver.getCurrentUrl());
					success = false;
					driver.quit();
					return;
				}
				waitForIt(5000); // Espera 5 segundos antes de tentar novamente
			}
		}

		// abaixar a tela
		for (int i = 0; i < 20; i++) {
			new Actions(driver).keyDown(Keys.ARROW_DOWN).perform();
		}

		// Clicando no botão
		waitForIt(15000);
		new Actions(driver).click(showMoreBtn).perform();

//Achando Oponente

		waitForIt(3000);

		String opponentText = null;

		WebElement firstOpp = null;
		buttonFound = false;
		attempts = 0;

		while (!buttonFound && attempts < 3) {
			try {
				firstOpp = driver
						.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[1]/div/div/div/div[2]/div[1]/bdi"));
				buttonFound = true;
			} catch (NoSuchElementException e) {
				attempts++;
				if (attempts == 3) {
					System.out.println("Texto não encontrado após 3 tentativas.");
					driver.quit();
					return;
				}
				waitForIt(5000); // Espera 5 segundos antes de tentar novamente
			}
		}

		opponentText = firstOpp.getText();

		System.out.println(opponentText);

		String[] opponentWords = opponentText.split("-");
		String opponent, team = "";
		team = opponentWords[0].substring(0, opponentWords[0].length() - 1);
		opponent = opponentWords[1].substring(1);

		System.out.println("Time no começo: " + team);
		System.out.println("Oponente no começo: " + opponent);

		// abaixar a tela
		waitForIt(15000);
		for (int i = 0; i < 25; i++) {
			new Actions(driver).keyDown(Keys.ARROW_DOWN).perform();
		}

		String matchDate = driver
				.findElements(By.xpath(
						"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[9]/div/div/div[1]/div/span[2]"))
				.get(0).getText();

		System.out.println("Data: " + matchDate);

		String competi = null;

		// Imprimindo o texto do time que esta no link

		String homeText = "Não existem estatísticas para a próxima partida do time de casa";
		String awayText = "Não existem estatísticas para a próxima partida do clube que esta jogando fora de casa ";
		waitForIt(10000);

		List<List<WebElement>> list = new ArrayList<>();
		List<WebElement> first = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div[2]/div[2]"));
		List<WebElement> second = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div[3]/div[2]"));
		List<WebElement> third = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div[2]/div[2]"));
		List<WebElement> fourth = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div[3]/div[2]"));
		List<WebElement> fith = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[3]/div/div[2]/div[1]/div[2]"));
		List<WebElement> six = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[3]/div/div[2]/div[2]/div[2]"));
		List<WebElement> seven = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div[2]/div[1]/div[2]"));
		List<WebElement> eight = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div[2]/div[1]/div[2]"));
		List<WebElement> nine = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[3]/div/div[2]/div/div[2]"));
		List<WebElement> ten = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div/div/div[2]"));
		List<WebElement> eleven = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div/div[2]/div[2]"));
		List<WebElement> twelve = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div/div[1]/div[2]"));
		List<WebElement> thirteen = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div/div[1]/div[2]"));
		List<WebElement> fourteen = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div/div[2]/div[2]"));
		List<WebElement> fithteen = driver.findElements(
				By.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div/div[2]/div[2]"));

		list.add(first);
		list.add(second);
		list.add(third);
		list.add(fourth);
		list.add(fith);
		list.add(six);
		list.add(seven);
		list.add(eight);
		list.add(nine);
		list.add(ten);
		list.add(eleven);
		list.add(twelve);
		list.add(thirteen);
		list.add(fourteen);
		list.add(fithteen);

		System.out.println("homeTeamName:" + homeTeamName);
		System.out.println("opponent:" + opponent);

		for (List<WebElement> x : list) {
			if (!x.isEmpty()) {
				System.out.println("Texto no for: " + x.get(0).getText());
				if (containsIgnoreCaseAndAccent(x.get(0).getText(), team)) {
					homeText = x.get(0).getText();
					System.out.println("Contem deu certo!");
				}
			}
		}

		for (List<WebElement> x : list) {
			if (!x.isEmpty()) {
				System.out.println("Texto no for: " + x.get(0).getText());
				if (containsIgnoreCaseAndAccent(x.get(0).getText(), opponent)) {
					awayText = x.get(0).getText();
					System.out.println("Contem deu certo!");
				}
			}
		}

		list.clear();

		List<WebElement> firstComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[11]/div[2]/div/span/div/div[1]/div[1]/a/span"));
		List<WebElement> secondComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[10]/div[2]/div/span/div/div[1]/div/a/span"));
		List<WebElement> thirdComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[11]/div/div/span/div/div[1]/div[1]/a/span"));
		List<WebElement> fourthComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[10]/div/div/span/div/div[1]/div[1]/a/span"));

		list.add(firstComp);
		list.add(secondComp);
		list.add(thirdComp);
		list.add(fourthComp);

		for (List<WebElement> x : list) {
			if (!x.isEmpty()) {
				competi = x.get(0).getText();
			}
		}

		try {
			System.out.println(generateMatchSummary(homeText, opponent, team, competi));
			summary = generateMatchSummary(homeText, opponent, team, competi);
			success = true;
		} catch (IllegalArgumentException e) {
			System.out.println(homeText);
			driver.quit();
			return;
		}

		String[] homeTextWords = homeText.split("\\s+");
		String[] awayTextWords = awayText.split("\\s+");
		System.out.println("homeText: " + homeText);
		System.out.println("awayText: " + awayText);
		System.out.println("team : " + team);
		System.out.println("opponent: " + opponent);

		if (homeTextWords.length < 17) {
			throw new IllegalArgumentException("O texto fornecido não contém palavras suficientes para processar.");
		}

		int n = 16;
		String homeOdd = homeTextWords[n];
		String awayOdd = awayTextWords[n];

		while (!homeOdd.contains("%")) {
			n++;
			if (n >= homeTextWords.length) {
				throw new IllegalArgumentException("Não foi possível encontrar uma odd no texto fornecido.");

			}
			homeOdd = homeTextWords[n];
		}
		n = 16;
		while (!awayOdd.contains("%")) {
			n++;
			if (n >= awayTextWords.length) {
				throw new IllegalArgumentException("Não foi possível encontrar uma odd no texto fornecido.");

			}
			awayOdd = awayTextWords[n];
		}

		this.homeTeamName = team;
		this.awayTeamName = opponent;
		this.competitionName = competi;
		this.date = matchDate;
		this.homeOdd = homeOdd;
		this.awayOdd = awayOdd;

		System.out.println("homeOdd: " + homeOdd);
		System.out.println("awayOdd: " + awayOdd);

		driver.quit();
	}

	private static void waitForIt(long tempo) {

		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean containsIgnoreCaseAndAccent(String source, String target) {
		String normalizedSource = normalize(source);
		String normalizedTarget = normalize(target);
		return normalizedSource.contains(normalizedTarget);
	}

	public static String normalize(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		normalized = pattern.matcher(normalized).replaceAll("").toLowerCase();
		return normalized.replace("-", " "); // Substitua '-' por espaço para consistência
	}

	public static String generateMatchSummary(String texto, String opponent, String team, String competi) {
		String[] textoWords = texto.split("\\s+");

		if (textoWords.length < 17) {
			throw new IllegalArgumentException("O texto fornecido não contém palavras suficientes para processar.");
		}

		int n = 16;
		String odd = textoWords[n];

		while (!odd.contains("%")) {
			n++;
			if (n >= textoWords.length) {
				throw new IllegalArgumentException("Não foi possível encontrar uma odd no texto fornecido.");

			}
			odd = textoWords[n];
		}

		StringBuilder sb = new StringBuilder();
		sb.append("O ");
		sb.append(team);
		sb.append(" tem ");
		sb.append(odd);
		sb.append(" de chance de vencer o ");
		sb.append(opponent);
		sb.append(" na próxima partida da competição: ");
		sb.append(competi);

		System.out.println("Time: " + team);
		System.out.println("Oponente: " + opponent);
		System.out.println("competi: " + competi);
		System.out.println("odd: " + odd);

		return sb.toString();
	}

}