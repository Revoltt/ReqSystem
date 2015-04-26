package operations;

import java.util.ArrayList;
import java.util.LinkedList;

import support.ActualLocation;
import support.Node;
import support.Requirement;
import support.Tree;

public class TransferManager {
	public static boolean nodeTextEqual(Node a, Node b)
	{
		String s1 = TextOps.regTransform(TextOps.nodeTextExtract(a));
		String s2 = TextOps.regTransform(TextOps.nodeTextExtract(b));
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
	
	public static String transfer()
	{
		int tr = 0; int all = 0;
		InterfaceOps.reqs2 = new ArrayList<Requirement>();
		for (int i = 0; i < InterfaceOps.reqs1.size(); i++)
		{
			String cur = transferRequirement(InterfaceOps.reqs1.get(i), InterfaceOps.tree2);
			String[] s = cur.split(" ");
			tr += Integer.valueOf(s[0]);
			all += Integer.valueOf(s[1]);
		}
		System.out.println("Transfer finished. " + tr + " of " + all + " Locations transfered");
		return tr + " " + all;
		
//		for (int i = 0; i < InterfaceOps.reqs2.size(); i++)
//		{
//			System.out.println("req:");
//			Requality curReq = InterfaceOps.reqs2.get(i);
//			for (int j = 0; j < curReq.getActualLocationlist().size(); j++)
//			{
//				System.out.println("loc:");
//				System.out.println(curReq.getActualLocationlist().get(j).getText());
//			}
//		}
	}
	
	public static String transferRequirement(Requirement r, Tree t2)
	{
		int sum = 0;
		ArrayList<ActualLocation> lst = r.getActualLocationlist();
		for (int i = 0; i < lst.size(); i++)
		{
			sum += transferActualLocation(r, lst.get(i), t2);
		}
//		if (sum == lst.size())
//			System.out.println("REQ: all Locations transfered");
//		else if (sum == 0)
//			System.out.println("REQ: transfer impossible");
//		else
//			System.out.println("REQ: partially transfered");
		return sum + " " + lst.size();
	}
	
	public static int transferActualLocation(Requirement r, ActualLocation src, Tree t2)
	{
		ArrayList<Node> path = src.getPath();
		ArrayList<Node> simPath = TransferManager.findSimilarPath(path, t2);
		path = TransferManager.pathTransform(path);
		String s1 = TransferManager.extractLowestSectionText(path);
		String s2 = TransferManager.extractLowestSectionText(simPath);
		s1 = TextOps.regTransform(s1);
		s2 = TextOps.regTransform(s2);
		String l = TextOps.regTransform(src.getText());
		int i1 = s1.indexOf(l);
		int i2 = s2.indexOf(l);
		if (i1 == -1)
		{
//			System.out.println("  LOC: Something went wrong with location:");
//			System.out.println(l);
			return 0;
		}
		else if (i2 != -1)
		{
			// exact match found, make transfer
		//	System.out.println("  LOC: Make transfer");
			ActualLocation temp = new ActualLocation(l);
			//System.out.println(l);
			temp.setPos(i2);
			temp.setPath(simPath);
			TreeRestorer.restoreActualLocation(r, temp);
			return 1;
		} else
		{
			//System.out.println("  LOC: Need magic!");
			return 0;
		}
	}
}
