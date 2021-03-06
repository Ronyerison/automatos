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
import br.ufpi.automatos.modelo.petri.PetriNet;

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
		String label;
		int pos = file.getName().lastIndexOf(".");
		if(pos != -1)
		   label = file.getName().substring(0, pos);
		else
			label = file.getName();
		String arquivo[] = lerArquivo(file).split("\n");
		Automato<InfoEstado, String> automato = new Automato<InfoEstado, String>();
		automato.setLabel(label);
		Estado<InfoEstado> inicial = new Estado<InfoEstado>(new InfoEstado(arquivo[0].trim()), true, false);
		automato.addEstado(inicial);
		String estadosFinais[] = arquivo[1].trim().split(";");
		for (String estadoFinal : estadosFinais) {
			if(inicial.getInfo().getLabel().equals(estadoFinal)){
				inicial.setMarcado(true);
				automato.getEstadosMarcados().add(inicial);
			}else{ 
				automato.addEstado(new Estado<InfoEstado>(new InfoEstado(estadoFinal), false, true));
			}
		}
		for (int i = 2; i < arquivo.length; i++) {
			String transicao[] = arquivo[i].trim().split(":");
			String estadosTransicao[] = transicao[1].split("->");
			Estado<InfoEstado> origem, destino;
			
			if(automato.getEstadoByLabel(estadosTransicao[0]) != null)
				origem = automato.getEstadoByLabel(estadosTransicao[0]);
			else
				origem = new Estado<InfoEstado>(new InfoEstado(estadosTransicao[0]));
			
			if(automato.getEstadoByLabel(estadosTransicao[1]) != null)
				destino = automato.getEstadoByLabel(estadosTransicao[1]);
			else
				destino = new Estado<InfoEstado>(new InfoEstado(estadosTransicao[1]));
			automato.addTransicao(new Transicao<String, InfoEstado>(transicao[0], origem, destino));
		}
		return automato;
	}
	
	public static PetriNet file2Petri(File arquivo){
		String label;
		int pos = arquivo.getName().lastIndexOf(".");
		if(pos != -1)
		   label = arquivo.getName().substring(0, pos);
		else
			label = arquivo.getName();
		String conteudo[] = lerArquivo(arquivo).split("\n");
		PetriNet petri = new PetriNet(label);
		String[] lugares = conteudo[0].trim().split(",");
		String[] transicoes = conteudo[1].trim().split(",");
		String[] arcos = conteudo[2].trim().split(",");
		String[] pesos = conteudo[3].trim().split(",");
		String[] tokens = conteudo[4].trim().split(",");
		
		for (int i = 0; i < lugares.length; i++) {
			petri.place(lugares[i].trim(), Integer.parseInt(tokens[i]));
		}
		
		for (int i = 0; i < transicoes.length; i++) {
			petri.transition(transicoes[i].trim());
		}
		
		for (int i = 0; i < arcos.length; i++) {
			String origem = arcos[i].substring(arcos[i].indexOf("(")+1, arcos[i].indexOf(";")).trim();
			String destino = arcos[i].substring(arcos[i].indexOf(";")+1, arcos[i].indexOf(")")).trim();
			
			if(petri.getPlaces().containsKey(origem) && petri.getTransitions().containsKey(destino)){
				petri.arc(pesos[i].trim(), petri.getPlaces().get(origem), petri.getTransitions().get(destino));
			}else if(petri.getTransitions().containsKey(origem) && petri.getPlaces().containsKey(destino)){
				petri.arc(pesos[i].trim(), petri.getTransitions().get(origem), petri.getPlaces().get(destino));
			}
			
		}
		return petri;
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
