package automatos;

import java.io.File;

import br.ufpi.automatos.algoritmos.Algoritmo;
import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.util.FileUtil;

public class Teste {

	public static void main(String[] args) {
		Automato<InfoEstado,String> automato = FileUtil.File2Automato(new File("C:\\Users\\Ronyerison\\Desktop\\automato2.txt"));
		Algoritmo<InfoEstado, String> algoritmo = new Algoritmo<InfoEstado, String>();
//		Automato<InfoEstado,String> acessibilidade = algoritmo.acessibilidade(automato);
//		System.out.println(acessibilidade.toString());
		Automato<InfoEstado, String> coacess = algoritmo.trim(automato);
		System.out.println(coacess.toString());
	}

}
