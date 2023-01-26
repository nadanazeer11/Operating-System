package Cpu;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class FileInterpreter {
	protected void readFileIntoList(String fileName, ArrayList<String> list) {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(fileName);

			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void readLineIntoList(String string, ArrayList<String> result) {
		String[] r = string.split("\\s+");
		for (int i = 0; i < r.length; i++) {
			result.add(r[i]);
		}
	}

	public void printCurrentLine(ArrayList<String> result) {
		System.out.print("Executing Line : ");
		for (int i = 0; i < result.size(); i++) {
			System.out.print(result.get(i) + " ");
		}
		System.out.println();
	}

}
