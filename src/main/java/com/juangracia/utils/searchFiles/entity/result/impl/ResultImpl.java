package com.juangracia.utils.searchFiles.entity.result.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juangracia.utils.searchFiles.entity.result.beans.Result;
import com.juangracia.utils.searchFiles.entity.result.interfaces.IResult;

@Component
public class ResultImpl implements IResult {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static String FILE_DIVIDER = ".";
	
	@Value("${searchfiles.search.this_path}")
	private String thisPath;
	
	@Value("${searchfiles.search.default_path}")
	private String defaultPath;
	
	@Value("${searchfiles.search.none_extension_value}")
	private String noneExtensionValue;
	
	@Override
	public ArrayList<Result> searchInDirectory(String path) {
		
		if(path.equalsIgnoreCase(thisPath)) {
			path = defaultPath;
		}
		
		ArrayList<Result> fileList = new ArrayList<Result>();
		
		try {
			
			File directory = new File(path);

			File[] files = directory.listFiles();
			
			fileList.addAll(searchInFolder(files));
			
			log.info("Files founded: {}", fileList.size());
		} catch (NullPointerException e) {
			log.error("Path [{}] invalid", path);
		}


		
		return fileList;

	}

	@Override
	public ArrayList<Result> filterByPattern(String pattern, ArrayList<Result> resultList) {
		ArrayList<Result> finalFileList = new ArrayList<Result>();
		Pattern patt = Pattern.compile(pattern);
		
		log.info("Filter by patter...");
		log.info("Pattern to find: {}", pattern);
		
		for (Result result : resultList) {		
			String fullName = result.getName().concat(result.getExtension());
			boolean matches = patt.matcher(fullName).find();
			
			if(matches) {
				finalFileList.add(result);
			}
		}
		
		log.info("Files with pattern [{}] founded: {}", pattern, finalFileList.size());
		
		return finalFileList;
	}

	private ArrayList<Result> searchInFolder(File[] files) throws NullPointerException {
		
		ArrayList<Result> fileList = new ArrayList<Result>();
		
		for (File file : files) {
			
			if (file.isDirectory()) {
				log.info("Searching in folder [{}]", file.getPath());
				if(file.listFiles() != null) {
					fileList.addAll(searchInFolder(file.listFiles()));
				}
			} else {
				Result fileFounded = new Result();
	
				
				
				fileFounded.setName(getFileName(file.getName()));
				fileFounded.setExtension(getFileExtension(file.getName()));
				fileFounded.setPath(file.getAbsolutePath());
				fileFounded.setLastModification(getLastModification(file));
				fileFounded.setSize(getFileKilobytes(file.length()));

				fileList.add(fileFounded);
			}

		}
		
		return fileList;

	}
	
	private String getLastModification(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		String lastModification = "";
		
		try {
			FileTime fileTime = Files.readAttributes(path, BasicFileAttributes.class).lastModifiedTime();
			LocalDateTime localDateTime = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			lastModification = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));;
		} catch (Exception e) {
			log.error("Se ha producido un error al obtener la fecha de ultima modificación");
		}
		
		return lastModification;
	    
	}

	private String getFileExtension(String name) {
		String extension = noneExtensionValue;
		if(name.contains(FILE_DIVIDER)) {
			int dot = name.lastIndexOf(FILE_DIVIDER);
			extension = name.substring(dot);
		}
		return extension;
	}

	private long getFileKilobytes(long bytes) {
		long kilobytes = bytes / 1024;
		return kilobytes;
	}

	private String getFileName(String name) {
		String finalName = name;
		if(name.contains(FILE_DIVIDER)) {
			int dot = name.lastIndexOf(FILE_DIVIDER);
			finalName = name.substring(0, dot);
		}
		return finalName;
	}


}
