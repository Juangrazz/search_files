package com.juangracia.utils.searchFiles.entity.result.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
		
		File directory = new File(path);

		File[] files = directory.listFiles();
		
		fileList.addAll(searchInFolder(files));

		log.info("Files founded: {}", fileList.size());
		
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

	private ArrayList<Result> searchInFolder(File[] files) {
		
		ArrayList<Result> fileList = new ArrayList<Result>();
		
		for (File file : files) {
			
			if (file.isDirectory()) {
				log.info("Searching in folder [{}]", file.getPath());
				fileList.addAll(searchInFolder(file.listFiles()));
			} else {
				Result fileFounded = new Result();
	
				fileFounded.setName(getFileName(file.getName()));
				fileFounded.setExtension(getFileExtension(file.getName()));
				fileFounded.setPath(file.getAbsolutePath());
				fileFounded.setSize(getFileKilobytes(file.length()));

				fileList.add(fileFounded);
			}

		}
		
		return fileList;

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
