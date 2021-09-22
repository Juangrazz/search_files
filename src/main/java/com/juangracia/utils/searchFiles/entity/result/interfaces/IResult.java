package com.juangracia.utils.searchFiles.entity.interfaces;

import java.util.ArrayList;

import com.juangracia.utils.searchFiles.entity.bean.Result;

public interface IResult {

	public ArrayList<Result> searchInDirectory(String path);
	public ArrayList<Result> filterByPattern(String pattern, ArrayList<Result> resultList);
	
}
