package petri;

import java.io.File;

import br.ufpi.automatos.algoritmos.petri.CoverageTree;
import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.petri.NodeInfo;
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
		CoverageTree coverageTree = new CoverageTree(petriNet);
		Automato<NodeInfo, String> automato = coverageTree.coverageTreeBuide();
		System.out.println(petriNet.toString());
	}
}
