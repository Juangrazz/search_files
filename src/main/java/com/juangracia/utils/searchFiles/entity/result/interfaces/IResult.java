package com.juangracia.utils.searchFiles.entity.result.interfaces;

import java.util.ArrayList;

import com.juangracia.utils.searchFiles.entity.result.beans.Result;

public interface IResult {

	public ArrayList<Result> searchInDirectory(String path);
	public ArrayList<Result> filterByPattern(String pattern, ArrayList<Result> resultList);
	
}
