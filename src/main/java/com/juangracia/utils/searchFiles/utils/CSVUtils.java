package com.juangracia.utils.searchFiles.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juangracia.utils.searchFiles.entity.result.beans.Result;

@Component
public class CSVUtils {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${searchfiles.csv.export_path}")
	private String path;
	
	@Value("${searchfiles.csv.export_name}")
	private String name;
	
	@Value("${searchfiles.csv.export_extension}")
	private String extension;	
	
	@Value("${searchfiles.csv.export_date_format}")
	private String dateFormat;

	public void createCsv(ArrayList<Result> results) {
		try {
			if(results.size() > 0) {
				String fileName = name.concat(getCurrentDateTime().concat(extension));
				String fullPath = path.concat(fileName);
				
				File file = new File(path);
				if(!file.exists()) {
					file.mkdirs();
				}
				
				FileWriter fileWriter = new FileWriter(fullPath, true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180);

				log.info("Creating CSV...");
				
				// HEADER
				csvPrinter.printRecord("Name", "Extension", "Size (Kb)", "Path");

				for (Result result : results) {
					// DATA
					csvPrinter.printRecord(result.getName(), result.getExtension(), result.getSize(), result.getPath());
				}

				csvPrinter.flush();
				
				log.info("CSV created into [{}{}]", path, fileName);
			} else {
				log.info("The CSV has not been created because there are no matches");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getCurrentDateTime() {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String dateString = format.format(new Date());

		return dateString;
	}

}
