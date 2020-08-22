package br.com.gestao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	
	public static void createFolder(String dirName) {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	public static void createFile(InputStream inStream, String target) throws IOException {
		OutputStream output = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		output = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			output.write(bytes, 0, read);
		}
		output.flush();
		output.close();
	}

}
