package com.juangracia.utils.searchFiles.entity.result.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString @EqualsAndHashCode
public class Result {

	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String extension;
	
	@Getter @Setter
	private long size;
	
	@Getter @Setter
	private String path;
	
}
