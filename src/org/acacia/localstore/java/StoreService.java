/**
Copyright 2015 Acacia Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

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
