package operations;

import java.util.ArrayList;

import support.Location;
import support.Node;
import support.Requality;
import support.Tree;

public class ReqExtractor {
	private static ArrayList<Requality> reqs = new ArrayList<Requality>();
	private static Requality cur;
	
	private static int isFound(String id)
	{
		for (int i = 0; i < reqs.size(); i++)
		{
			if (reqs.get(i).getId().equals(id))
				return i;
		}
		return -1;
	}
	
	private static void goTree(Node n)
	{
		if (n.getType().equals("Requality"))
		{
			String id = n.getId();
			int i = isFound(id);
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
