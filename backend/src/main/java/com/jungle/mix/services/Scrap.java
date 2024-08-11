package com.jungle.mix.services;

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
import org.springframework.stereotype.Service;

@Service
public class Scrap {

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

		String name = driver
				.findElement(By.xpath(
						"//*[@id=\"__next\"]/main/div[2]/div/div[2]/div[1]/div[1]/div/div[2]/div/div[1]/div[2]/h2"))
				.getText();

		System.out.println("Home team: " + homeTeam);

		WebElement showMoreBtn = null;
		boolean buttonFound = false;
		int attempts = 0;

		while (!buttonFound && attempts < 3) {
			try {
				showMoreBtn = driver.findElement(By.xpath(
						"//*[@id='__next']/main/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div[2]/div/div[1]/div/div/a/button"));
				buttonFound = true;
			} catch (NoSuchElementException e) {
				attempts++;
				if (attempts == 3) {
					System.out.println("Botão 'Show More' não encontrado após 3 tentativas.");
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

		// abaixar a tela
		waitForIt(15000);
		for (int i = 0; i < 20; i++) {
			new Actions(driver).keyDown(Keys.ARROW_DOWN).perform();
		}

		String competi = null;

		// Imprimindo o texto do time que esta no link

		String texto = "Não existem estatísticas para a próxima partida do " + homeTeam;

		List<List<WebElement>> list = new ArrayList<>();
		waitForIt(10000);

		List<WebElement> first = driver
				.findElements(By.xpath("//*[@id='__next']/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div[2]/div[2]"));
		List<WebElement> second = driver
				.findElements(By.xpath("//*[@id='__next']/main/div[2]/div[2]/div[1]/div[2]/div[5]/div/div[3]/div[2]"));
		List<WebElement> third = driver
				.findElements(By.xpath("//*[@id='__next']/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div[2]/div[2]"));
		List<WebElement> fourth = driver
				.findElements(By.xpath("//*[@id='__next']/main/div[2]/div[2]/div[1]/div[2]/div[4]/div/div[3]/div[2]"));

		list.add(first);
		list.add(second);
		list.add(third);
		list.add(fourth);

		for (List<WebElement> x : list) {
			if (!x.isEmpty() && containsIgnoreCaseAndAccent(x.get(0).getText(), homeTeam)
					|| !x.isEmpty() && containsIgnoreCaseAndAccent(x.get(0).getText(), name)) {
				texto = x.get(0).getText();
				buttonFound = true;
			}
		}

		list.clear();

		List<WebElement> firstComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[12]/div[1]/div[2]/div/div[1]/div[1]/a/span"));
		List<WebElement> secondComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[11]/div[1]/div[2]/div/div[1]/div/a/span"));
		List<WebElement> thirdComp = driver.findElements(By.xpath(
				"//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[14]/div[1]/div[2]/div/div[1]/div[1]/a/span"));

		list.add(firstComp);
		list.add(secondComp);
		list.add(thirdComp);
		for (List<WebElement> x : list) {
			if (!x.isEmpty()) {
				competi = x.get(0).getText();
			}
		}

		String data = null;

		list.clear();

		List<WebElement> firstData = driver.findElements(By
				.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[12]/div[1]/div[2]/div/div[1]/div[1]"));
		List<WebElement> secondData = driver.findElements(By
				.xpath("//*[@id=\"__next\"]/main/div[2]/div[2]/div[1]/div[1]/div[11]/div[1]/div[2]/div/div[1]/div[1]"));

		list.add(firstData);
		list.add(secondData);
		for (List<WebElement> x : list) {
			if (!x.isEmpty()) {
				data = x.get(0).getText();
			}
		}

		System.out.println(texto);
		System.out.println(competi);
		System.out.println(data);
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
}