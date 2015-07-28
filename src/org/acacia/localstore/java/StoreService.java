package org.acacia.localstore.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreService {

	public void readValues() {
		try {
			File f = new File("relationMap.txt");
			Scanner sc = new Scanner(f);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				List<Integer> numbers = new ArrayList<Integer>();
				for (String part : line.split(" ")) {
					Integer i = Integer.valueOf(part);
					numbers.add(i);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
