package operations;

import java.util.ArrayList;

import support.Node;
import support.Tree;

public class TransferManager {
	public static boolean nodeTextEqual(Node a, Node b)
	{
		String s1 = TextOps.nodeTextExtract(a).replaceAll("\\s$", "");
		s1 = s1.replaceAll("\r?\n", " ").replaceAll("\t", " ").replaceAll("\\s{2,}", " ");
		s1 = s1.replaceAll("\\s[\\.]", ".").replaceAll("\\s[,]", ",");
		String s2 = TextOps.nodeTextExtract(b).replaceAll("\\s$", "");
		s2 = s2.replaceAll("\r?\n", " ").replaceAll("\t", " ").replaceAll("\\s{2,}", " ");
		s2 = s2.replaceAll("\\s[\\.]", ".").replaceAll("\\s[,]", ",");
		return s1.equals(s2);
	}
	
	public static boolean typeEqual(Node a, Node b)
	{
		return a.getType().equals(b.getType());
	}
	
	public static ArrayList<Node> pathTransform(ArrayList<Node> path)
	{
		ArrayList<Node> temp = new ArrayList<Node>();
		for (int i = path.size() - 1; i >= 0; i--)
		{
				temp.add(path.get(i));
		}
		return temp;
	}
	
	public static ArrayList<Node> findSimilarPath(ArrayList<Node> p, Tree t) // find a path, similar to path from tree1, in tree2 
	{
		Node cur = t.getRoot();
		ArrayList<Node> res = new ArrayList<Node>();
		ArrayList<Node> path = pathTransform(p);
		//Node lastHeaderNode = cur;
		int k = 1;
		if (path.size() == 0)
		{
			res.add(cur);
			return res;
		}
		else
			while (k < path.size())
			{
				boolean found = false;
				for (int i = 0; i < cur.getChildren().size(); i++)
				{
					if (typeEqual(cur.getChildren().get(i), path.get(k)) && nodeTextEqual(cur.getChildren().get(i), path.get(k)))
					{
						res.add(cur);
						cur = cur.getChildren().get(i);
						k++;
						found = true;
						break;
					}
				}
				if (!found)
					break;
			}
		res.add(cur);
		return res;
	}
	
	public static String extractLowestSectionText(ArrayList<Node> path)
	{
		int i = path.size() - 1;
		while (i >= 0)
		{
			if (path.get(i).getType().startsWith("h") || path.get(i).getType().equals("body"))
				break;
			i--;
		}
		return TextOps.sectionTextExtract(path.get(i));
	}
}
