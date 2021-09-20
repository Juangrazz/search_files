package com.juangracia.utils.searchFiles.entity.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.juangracia.utils.searchFiles.entity.bean.Result;
import com.juangracia.utils.searchFiles.entity.interfaces.IResult;

public class ResultImpl implements IResult {

	

	@Override
	public ArrayList<Result> searchInDirectory(String path) {
		ArrayList<Result> fileList = new ArrayList<Result>();
		
		File directory = new File(path);

		File[] files = directory.listFiles();
		
		fileList.addAll(searchInFolder(files));

		return fileList;

	}

	@Override
	public ArrayList<Result> filterByPattern(String pattern, ArrayList<Result> resultList) {
		ArrayList<Result> finalFileList = new ArrayList<Result>();
		
		Pattern patt = Pattern.compile(pattern);
		
		for (Result result : resultList) {		
			String fullName = result.getName().concat(result.getExtension());
			boolean matches = patt.matcher(fullName).find();
			
			if(matches) {
				finalFileList.add(result);
			}
		}
		
		return finalFileList;
	}

	private ArrayList<Result> searchInFolder(File[] files) {
		
		ArrayList<Result> fileList = new ArrayList<Result>();
		
		for (File file : files) {

			if (file.isDirectory()) {
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
		String extension = "-- None --";
		if(name.contains(".")) {
			int dot = name.lastIndexOf('.');
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
		if(name.contains(".")) {
			int dot = name.lastIndexOf('.');
			finalName = name.substring(0, dot);
		}
		return finalName;
	}


}
