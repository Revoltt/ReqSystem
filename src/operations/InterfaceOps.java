package operations;

import googleDiff.diff_match_patch.Diff;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.jdom2.*;

import support.DOMStructBuilder;
import support.Location;
import support.Node;
import support.Requality;
import support.Tree;

public class InterfaceOps {
	static Tree tree1;
	static Tree tree2;
	static ArrayList<Requality> reqs1;
	static ArrayList<Requality> reqs2;
	
	public static void makeTrees(String filename1, String filename2) throws FileNotFoundException
	{
		Document d1 = DOMStructBuilder.getDocumentFromFile(filename1);
		tree1 = new Tree();
		tree1.makeTreeFromDoc(d1);
		
		Document d2 = DOMStructBuilder.getDocumentFromFile(filename2);
		tree2 = new Tree();
		tree2.makeTreeFromDoc(d2);
		
	}
	
	public static void getReqs(Tree t)
	{
		reqs1 = ReqExtractor.extractReqsFromTree(t);
	}
	
	public static ArrayList<Node> getLocationPath(Location l)
	{
		ArrayList<Node> path = new ArrayList<Node>();
		Node n = l.getNode();
		while (n.getParent() != null)
		{
			n = n.getParent();
			path.add(n);
		}
		return path;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		makeTrees("etsip3h5_old.xhtml", "etsip3h5_new.xhtml");
		//makeTrees("Draft_ETSI_TS_103 097 v1.1.12.xhtml", "Draft_ETSI_TS_103 097 v1.1.14.xhtml");
		getReqs(tree1);

		TextOps.createActualLocations();

		TransferManager.transfer();	
		
		ReqRestorer.restoreLocationsInTree();
		
		tree2.out = new PrintStream("restoredTree.txt");
		tree2.printMyTree(tree2.getRoot(), "");
		
//		@SuppressWarnings("resource")
//		PrintStream fout = new PrintStream("sections.txt");
//		// output of all actual locations and their paths, and similar paths in the second document
//		int q = 0;
//		int errors = 0;
//		for (int i = 0; i < reqs1.size(); i++)
//		{
//			fout.println("REQUALITY " + i);
//			Requality curReq = reqs1.get(i);
//			for (int j = 0; j < curReq.getActualLocationlist().size(); j++)
//			{
//				fout.println("LOCATION " + j);
//				fout.println(curReq.getActualLocationlist().get(j).getText());
//				ArrayList<Node> path = curReq.getActualLocationlist().get(j).getPath();
//				
//				fout.println("PATH:");
//				for (int k = 0; k < path.size(); k++)
//				{
//					fout.println(path.get(k).getType() + " " + TextOps.nodeTextExtract(path.get(k)));
//				}
//				
////				System.out.println("SIMILAR PATH FOUND:");
////				ArrayList<Node> simPath = TransferManager.findSimilarPath(path, tree2);
////				for (int k = simPath.size() - 1; k >= 0; k--)
////					fout.println(simPath.get(k).getType() + " " + TextOps.nodeTextExtract(simPath.get(k)));
//				ArrayList<Node> simPath = TransferManager.findSimilarPath(path, tree2);
//				path = TransferManager.pathTransform(path);
//				String s1 = TransferManager.extractLowestSectionText(path);
//				String s2 = TransferManager.extractLowestSectionText(simPath);
//				
//				System.out.print(q + " ");
//				
//				s1 = TextOps.regTransform(s1);
//				s2 = TextOps.regTransform(s2);
//				
//				fout.println("LOWEST HEADER OF TEXT1");
//				fout.println(s1);
//				fout.println("LOWEST HEADER OF TEXT2");
//				fout.println(s2);
//				fout.println();
//				
//				ArrayList<Diff> diffResult = TransferManager.diff(s1, s2);
//				String l = TextOps.regTransform(curReq.getActualLocationlist().get(j).getText());
//				
//				//we need to find exactly what symbol causes the problem,
//				//so we shall run a loop to compare l and s1 symbol by symbol
////				int c = 0;
////				for (int k = 0; k < s1.length(); k++)
////				{
////					if (c == l.length())
////						break;
////					if (s1.charAt(k) == l.charAt(c))
////						c++;
////					else 
////						c = 0;
////				}
//				System.out.println(diffResult.size() + " " + s1.indexOf(l) + " " + s2.indexOf(l));
//				if (s2.indexOf(l) == -1)
//					errors++;
//				q++;
//			}
//			fout.println("------------------------------");
//		}
//		System.out.println(errors);
		
		// get section text and section text memory tests
		//TextOps.sectionTextExtract(reqs1.get(8).getLocationlist().get(0).getNode().getParent().getParent().getParent().getParent());
		//System.out.println(reqs1.get(8).getLocationlist().get(0).getNode().getParent().getParent().getParent().getParent().getAllText());
	
		
	}
}
