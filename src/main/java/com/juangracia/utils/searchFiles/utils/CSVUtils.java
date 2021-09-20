package com.juangracia.utils.searchFiles.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juangracia.utils.searchFiles.entity.bean.Result;

@Component
public class CSVUtils {

	
	private String path = "./";
	
	private String name = "files_";
	
	private String extension = ".csv";

	public void createCsv(ArrayList<Result> results) {
		try {
			String fullPath = path.concat(name.concat(getCurrentDateTime().concat(extension)));
			FileWriter fileWriter = new FileWriter(fullPath, true);
			CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180);

			// HEADER
			csvPrinter.printRecord("Name", "Extension", "Size (Kb)", "Path");

			for (Result result : results) {
				// DATA
				csvPrinter.printRecord(result.getName(), result.getExtension(), result.getSize(), result.getPath());
			}

			csvPrinter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getCurrentDateTime() {
		SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SSS");
		String dateString = format.format(new Date());

		return dateString;
	}

}
