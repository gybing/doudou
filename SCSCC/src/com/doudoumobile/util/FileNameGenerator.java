package com.doudoumobile.util;

public class FileNameGenerator {
	public static String getFileName(String oldName) {
		String newName = "";
		newName = Base64.encode(oldName.getBytes());
		newName = newName.replace("=","OOXX");
		return newName;
	}

	public static void main(String[] args) {
		System.out.println(getFileName("Na na na goodbye song.m4a"));
	}
}
