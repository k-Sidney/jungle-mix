package com.jungle.mix.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadAndWrite {
	private ArrayList<String> timesENumeros;

	public ReadAndWrite() {
		timesENumeros = new ArrayList<>();
	}

	public void read(String filePath) {
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

	public void write(String summary) {
		try {
			// Caminho relativo para a pasta resources no diretório de execução
			String directoryPath = new File("resources").getAbsolutePath();
			File directory = new File(directoryPath);

			// Se a pasta não existir, cria
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Caminho completo do arquivo
			String filePath = directoryPath + "/match_summary.txt";

			// Escrevendo no arquivo de texto
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
				writer.write(summary);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<String> getTimesENumeros() {
		return timesENumeros;
	}
}
