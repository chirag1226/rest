package com.rest.models;

public class ProgramCodes {

	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProgramCodes [title=" + title + ", description=" + description + "]";
	}

	public ProgramCodes(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	public ProgramCodes() {
		super();
	}
}
