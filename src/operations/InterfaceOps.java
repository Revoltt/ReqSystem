package operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.jdom2.*;

import support.DOMStructBuilder;
import support.Location;
import support.Node;
import support.Requirement;
import support.Tree;
import testing.TestClass;

public class InterfaceOps {
	static Tree tree1;
	static Tree tree2;
	static ArrayList<Requirement> reqs1;
	static ArrayList<Requirement> reqs2;
	
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
	
	public static String run(String f1, String f2) throws FileNotFoundException
	{
		String s = "0 0";
		System.out.print(f1 + " "); 
		if ((new File(f1)).exists() && (new File(f2)).exists())
		{
			long startTime = System.currentTimeMillis();
			makeTrees(f1, f2);
			getReqs(tree1);

			TextOps.createActualLocations();

			s = TransferManager.transfer();	
			
			TreeRestorer.restoreLocationsInTree();
			
			long timeSpent = System.currentTimeMillis() - startTime;
			System.out.println("Run time " + timeSpent + " ms");
		} else
		{
			System.out.println("FILE NOT FOUND");
		}
		return s;
	}
	
	public static void main(String[] args) throws IOException
	{
//		run("etsip3h5_old.xhtml", "etsip3h5_new.xhtml");
//		run("etsip5h7_old.xhtml", "etsip5h7_new.xhtml");
//		run("etsip4h6_old.xhtml", "etsip4h6_new.xhtml");
//		
//		run("Draft_ETSI_TS_103 097 v1.1.12.xhtml", "Draft_ETSI_TS_103 097 v1.1.14.xhtml");
//		run("posix/v3/fscanf.html.xhtml", "posix/v4/fscanf.html.xhtml");
//		run("posix/v3/fprintf.html.xhtml", "posix/v4/fprintf.html.xhtml");
//		run("posix/v3/fwprintf.html.xhtml", "posix/v4/fwprintf.html.xhtml");
//		run("posix/v3/environ.html.xhtml", "posix/v4/environ.html.xhtml");
//		run("lsb/v3/zlib-deflateinit2.html.xhtml", "lsb/v4/zlib-deflateinit2.html.xhtml");
//		run("lsb/v3/zlib-deflate-1.html.xhtml", "lsb/v4/zlib-deflate-1.html.xhtml");
//		run("lsb/v3/libutil-getopt-3.html.xhtml", "lsb/v4/libutil-getopt-3.html.xhtml");
		run("testold.xhtml", "testnew1.xhtml");
		//TestClass.testPosix();
		//TestClass.testLsb();
		
		//tree2.out = new PrintStream("tagsMy.txt");
		//tree2.printMyTree(tree2.getRoot(),"");
		
//		for (int i = 0; i < reqs2.size(); i++)
//		{
//			for (int j = 0; j < reqs2.get(i).getActualLocationlist().size(); j++)
//				System.out.println(reqs2.get(i).getActualLocationlist().get(j).getText());
//			System.out.println();
//		}
		
		
		//tree2.out = new PrintStream("restoredTree.txt");
		//tree2.printMyTree(tree2.getRoot(), "");
		
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
