package pl.oblivion.utils;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

	public static String loadResources(String fileName) throws Exception {
		String result;
		try (InputStream in = Utils.class.getClassLoader().getResourceAsStream(fileName);
		     Scanner scanner = new Scanner(in, "UTF-8")) {
			result = scanner.useDelimiter("\\A").next();
		}
		return result;
	}
}
