package com.juangracia.utils.searchFiles;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.juangracia.utils.searchFiles.entity.bean.Result;
import com.juangracia.utils.searchFiles.entity.impl.ResultImpl;
import com.juangracia.utils.searchFiles.utils.CSVUtils;

@Component
public class RunnerSearchFile {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private CSVUtils csvUtils = new CSVUtils();
	private ResultImpl resultImpl = new ResultImpl();
	
	public void main(String[] args) {
		
		if(args.length != 2) {
			throw new IllegalArgumentException("Invalid arguments");
		}
		
		ArrayList<Result> resultList = resultImpl.searchInDirectory(args[0]);
		ArrayList<Result> finalResultList = resultImpl.filterByPattern(args[1], resultList);
		
		csvUtils.createCsv(finalResultList);
		
	}

}
