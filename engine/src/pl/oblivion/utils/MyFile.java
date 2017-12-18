package pl.oblivion.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyFile {

	private static final String FILE_SEPARATOR = "/";

	private String path;
	private String name;

	public MyFile(String path) {
		this.path = FILE_SEPARATOR + path;
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	public MyFile(String... paths) {
		this.path = "";
		for (String part : paths) {
			this.path += (FILE_SEPARATOR + part);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	public MyFile(MyFile file, String subFile) {
		this.path = file.path + FILE_SEPARATOR + subFile;
		this.name = subFile;
	}

	public MyFile(MyFile file, String... subFiles) {
		this.path = file.path;
		for (String part : subFiles) {
			this.path += (FILE_SEPARATOR + part);
		}
		String[] dirs = path.split(FILE_SEPARATOR);
		this.name = dirs[dirs.length - 1];
	}

	@Override
	public String toString() {
		return getPath();
	}

	public String getPath() {
		return path;
	}

	public BufferedReader getReader() {
		try {
			InputStreamReader isr = new InputStreamReader(getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			return reader;
		} catch (Exception e) {
			System.err.println("Couldn't get reader for " + path);
			throw e;
		}
	}

	public InputStream getInputStream() {
		return Class.class.getResourceAsStream(path);
	}

	public String getName() {
		return name;
	}
}
