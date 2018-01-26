package com.ibm.cdl.util;

import java.io.File;

public class FileCommonUtils {

	public static void deleteFile(String fileName) {
		
		try {
			File file = new File(fileName);
			
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(File file,String fileName) {
		
		try {
			
			File saveFile = new File(fileName);
			
			if (file.exists()) file.createNewFile();
			org.apache.commons.io.FileUtils.copyFile(file, saveFile);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
