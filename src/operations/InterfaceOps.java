package operations;

import java.io.FileNotFoundException;
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
		Document d2 = DOMStructBuilder.getDocumentFromFile(filename2);
		tree2 = new Tree();
		tree2.makeTreeFromDoc(d2);
		
		Document d1 = DOMStructBuilder.getDocumentFromFile(filename1);
		tree1 = new Tree();
		tree1.makeTreeFromDoc(d1);
		
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
		makeTrees("Draft_ETSI_TS_103 097 v1.1.12.xhtml", "Draft_ETSI_TS_103 097 v1.1.14.xhtml");
		getReqs(tree1);
		
		TextOps.createActualLocations();
		// output of all actual locations and their paths
		for (int i = 0; i < reqs1.size(); i++)
		{
			System.out.println("REQUALITY " + i);
			Requality curReq = reqs1.get(i);
			for (int j = 0; j < curReq.getActualLocationlist().size(); j++)
			{
				System.out.println("LOCATION " + j);
				System.out.println(curReq.getActualLocationlist().get(j).getText());
				ArrayList<Node> path = curReq.getActualLocationlist().get(j).getPath();
				
				System.out.println("PATH:");
				for (int k = 0; k < path.size(); k++)
				{
					System.out.println(path.get(k).getType() + " " + TextOps.nodeTextExtract(path.get(k)));
				}
				System.out.println();
			}
			System.out.println("------------------------------");
			
		}
		
		// get section text test
		System.out.println(TextOps.sectionTextExtract(reqs1.get(8).getLocationlist().get(0).getNode().getParent().getParent().getParent().getParent()));
	}
}