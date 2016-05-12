package br.ufpi.automatos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.UploadedFile;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.Transicao;

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
	
	public static Automato<InfoEstado, String> File2Automato (File file){
		String arquivo[] = lerArquivo(file).split("\n");
		Automato<InfoEstado, String> automato = new Automato<InfoEstado, String>();
		Estado<InfoEstado> inicial = new Estado<InfoEstado>(new InfoEstado(arquivo[0]), true, false);
		automato.addEstado(inicial);
		String estadosFinais[] = arquivo[1].split(",");
		for (String estadoFinal : estadosFinais) {
			if(inicial.getInfo().getLabel().equals(estadoFinal))
				inicial.setMarcado(true);
			else 
				automato.addEstado(new Estado<InfoEstado>(new InfoEstado(estadoFinal), false, true));
		}
		for (int i = 2; i < arquivo.length; i++) {
			String transicao[] = arquivo[i].split(":");
			String estadosTransicao[] = transicao[1].split("->");
			Estado<InfoEstado> origem, destino;
			if(automato.getEstados().contains(estadosTransicao[0]))
				origem = automato.getEstadoByLabel(estadosTransicao[0]);
			else
				origem = new Estado<InfoEstado>(new InfoEstado(estadosTransicao[0]));
			if(automato.getEstados().contains(estadosTransicao[1]))
				destino = automato.getEstadoByLabel(estadosTransicao[1]);
			else
				destino = new Estado<InfoEstado>(new InfoEstado(estadosTransicao[1]));
			automato.addTransicao(new Transicao<String, InfoEstado>(transicao[0], origem, destino));
		}
		return automato;
	}
	
	public static String lerArquivo(File file) {
		StringBuffer sb = new StringBuffer();
		try {
			FileReader reader = new FileReader(file);
			int c;
			do {
				c = reader.read();
				if (c != -1) {
					sb.append((char) c);
				}
			} while (c != -1);
			reader.close();
		} catch (IOException e) {
			System.out.println("Arquivo invalido!");
		}
		return sb.toString();
	}
}
