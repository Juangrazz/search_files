package com.juangracia.utils.searchFiles;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juangracia.utils.searchFiles.entity.result.beans.Result;
import com.juangracia.utils.searchFiles.entity.result.impl.ResultImpl;
import com.juangracia.utils.searchFiles.utils.CSVUtils;

@Component
public class RunnerSearchFile {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CSVUtils csvUtils;
	
	@Autowired
	private ResultImpl resultImpl;
	
	@Value("${searchfiles.search.this_path}")
	private String thisPath;
	
	public void main(String[] args) {
		if(args.length != 2) {
			log.error("Invalid number of arguments");
			throw new IllegalArgumentException("Invalid number of arguments");
		}
		
		if(!args[0].contains("\\") && !args[0].equals(thisPath)) {
			log.error("Ruta no válida");
			throw new IllegalArgumentException("Invalid path");
		}
		
		for (int i = 0; i < args.length; i++) {
			log.info("Arg {} = {}", i, args[i]);
		}
		
		ArrayList<Result> resultList = resultImpl.searchInDirectory(args[0]);
		
		if(!resultList.isEmpty()) {
			ArrayList<Result> finalResultList = resultImpl.filterByPattern(args[1], resultList);
			csvUtils.createCsv(finalResultList);
		}
	}

}
