package com.jungle.mix.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadClubs {
	private ArrayList<String> timesENumeros;

	public ReadClubs() {
		timesENumeros = new ArrayList<>();
	}

	public void lerArquivo(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				String[] entries = linha.split("\\|");
				for (String entry : entries) {
					timesENumeros.add(entry);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getTimesENumeros() {
		return timesENumeros;
	}
}
