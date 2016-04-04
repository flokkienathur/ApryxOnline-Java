package net.apryx.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOUtils {
	
	public static String fileToString(File file){
		StringBuilder out = new StringBuilder();
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(s.hasNextLine()){
			out.append(s.nextLine());
			out.append('\n');
		}
		s.close();
		return out.toString();
	}
	
	public static String fileToString(String path){
		File file = new File(path);
		return fileToString(file);
	}
}
