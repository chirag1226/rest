package com.rest.models;

public class FileNamePath {
	private String name;
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FileNamePath(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	@Override
	public String toString() {
		return "FileNamePath [name=" + name + ", path=" + path + "]";
	}

	public FileNamePath() {
		super();
	}
}
