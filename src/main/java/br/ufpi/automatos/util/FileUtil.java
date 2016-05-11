package br.ufpi.automatos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.UploadedFile;

public class FileUtil {
	
	public static File uploadedFileToFile(UploadedFile uploadedFile) throws IOException{
		
		InputStream in;
		in = uploadedFile.getInputstream();
		File file = new File(System.getProperty("java.io.tmpdir"),
				uploadedFile.getFileName());
		FileOutputStream fos = new FileOutputStream(file);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = in.read(bytes)) != -1) {
			fos.write(bytes, 0, read);
		}

		in.close();
		fos.flush();
		fos.close();
		
		return file;
	}
}
