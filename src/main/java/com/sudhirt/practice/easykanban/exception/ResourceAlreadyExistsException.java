package com.sudhirt.practice.easykanban.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 524185950485534171L;

	private String type;

	private String identifierName;

	private String identifierValue;

	public ResourceAlreadyExistsException(String type, String identifierName, String identifierValue) {
		super(type + " with '" + identifierName + "' - '" + identifierValue + "' already exists");
		this.type = type;
		this.identifierName = identifierName;
		this.identifierValue = identifierValue;
	}

	public ResourceAlreadyExistsException(String type, String identifierName, long identifierValue) {
		super(type + " with '" + identifierName + "' - '" + identifierValue + "' already exists");
		this.type = type;
		this.identifierName = identifierName;
		this.identifierValue = String.valueOf(identifierValue);
	}

}