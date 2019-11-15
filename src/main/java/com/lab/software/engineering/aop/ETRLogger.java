package com.lab.software.engineering.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ETRLogger {

	
	public static void logToFile(String message) {
		FileWriter writer = null;
		try {
			writer = new FileWriter("log.txt", true);
			writer.write(getDateAndTime() + " " +  message);
			writer.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	public static String getDateAndTime() {
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
}
