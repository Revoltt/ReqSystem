package operations;

import java.util.ArrayList;

import support.Location;
import support.Node;
import support.Requirement;
import support.Tree;

public class ReqExtractor {
	private static ArrayList<Requirement> reqs = new ArrayList<Requirement>();
	private static Requirement cur;
	
	public static int isFound(String id, ArrayList<Requirement> rlst)
	{
		for (int i = 0; i < rlst.size(); i++)
		{
			if (rlst.get(i).getId().equals(id))
				return i;
		}
		return -1;
	}
	
	private static void goTree(Node n)
	{
		if (n.getType().equals("requirement"))
		{
			String id = n.getId();
			int i = isFound(id, reqs);
			Location x = new Location(n);
			if (i == -1)
			{
				cur = new Requirement();
				cur.setId(id);
				cur.addLocation(x);
				reqs.add(cur);
			} else
			{
				reqs.get(i).addLocation(x);
			}
		}
		if (n.getChildren() != null)
			for (int i = 0; i < n.getChildren().size(); i++)
			{
				goTree(n.getChildren().get(i));
			}
	}
	
	public static ArrayList<Requirement> extractReqsFromTree(Tree t)
	{
		reqs = new ArrayList<Requirement>();
		goTree(t.getRoot());
		return reqs;
	}
}
