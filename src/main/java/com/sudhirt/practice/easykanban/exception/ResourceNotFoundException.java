package com.sudhirt.practice.easykanban.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3882429263802463234L;

	private final String type;

	private final String id;

	public ResourceNotFoundException(String type, Long id) {
		super(type + " with identifier '" + id + "' not found");
		this.type = type;
		this.id = String.valueOf(id);
	}

}