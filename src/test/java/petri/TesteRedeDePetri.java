package petri;

import java.io.File;

import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.util.FileUtil;

public class TesteRedeDePetri {

	public static void main(String[] args) {
		imprimeRede();
	}

	public static void imprimeRede() {
//		PetriNet petriNet = FileUtil.file2Petri(new File("C:\\Users\\Ronyerison\\Desktop\\testes\\NETTeste.txt"));
//		CoverageTree tree = new CoverageTree(petriNet);
//		tree.coverageTreeBuide();
		PetriNet petriNet = FileUtil.file2Petri(new File("/home/rafael/Documentos/coverageTree.txt"));
		
		System.out.println(petriNet.toString());
	}
}
