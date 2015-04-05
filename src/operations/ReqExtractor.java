package operations;

import java.util.ArrayList;

import support.Location;
import support.Node;
import support.Requality;
import support.Tree;

public class ReqExtractor {
	private static ArrayList<Requality> reqs = new ArrayList<Requality>();
	private static Requality cur;
	
	public static int isFound(String id, ArrayList<Requality> rlst)
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
		if (n.getType().equals("requality"))
		{
			String id = n.getId();
			int i = isFound(id, reqs);
			Location x = new Location(n);
			if (i == -1)
			{
				cur = new Requality();
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
	
	public static ArrayList<Requality> extractReqsFromTree(Tree t)
	{
		goTree(t.getRoot());
		return reqs;
	}
}
