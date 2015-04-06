package operations;

import java.util.ArrayList;

import support.ActualLocation;
import support.Location;
import support.Node;
import support.Requality;

public class ReqRestorer {
	
	public static void restoreActualLocation(Requality r, ActualLocation l)
	{
		int index = ReqExtractor.isFound(r.getId(), InterfaceOps.reqs2);
		if ( index == -1)
		{
			Requality cur = new Requality();
			cur.setId(r.getId());
			cur.addActualLocation(l);
			InterfaceOps.reqs2.add(cur);
		} else
		{
			InterfaceOps.reqs2.get(index).addActualLocation(l);
		}
	}
	
	private static void createLocation(Node n, int start, int end, Requality r)
	{
		// n is text node
		String text = n.getText();
		int realStart = 0, realEnd = 0;
		Node parent = n.getParent();
		int childPos = 0;
		for (int i = 0; i < parent.getChildren().size(); i++)
		{
			if (parent.getChildren().get(i).equals(n))
			{
				childPos = i;
				break;
			}
		}
		for (int i = 0; i < text.length(); i++)
		{
			if (text.substring(0, i).length() == start)
			{
				n.setText(text.substring(0, i));
				realStart = i;
			}
			if (text.substring(0, i + 1).length() == end)
			{
				realEnd = i;
				break;
			}
		}
		
		if (n.getText().equals(""))
		{
			parent.getChildren().remove(childPos);
			childPos--;
		}
		// requality(location) node
		Node loc = new Node();
		loc.setDepth(parent.getDepth() + 1);
		if (r.getLocationlist().size() == 0)
			loc.setA(true);
		else
			loc.setA(false);
		loc.setId(r.getId());
		loc.setParent(parent);
		loc.setType("requality");
		parent.insertChild(loc, childPos + 1);
		r.addLocation(new Location(loc));
		
		// location text node
		Node locText = new Node();
		locText.setDepth(parent.getDepth() + 2);
		locText.setParent(loc);
		locText.setType("text");
		locText.setText(text.substring(realStart, realEnd + 1));
		loc.addChild(locText);
		
		// other text node (goes after location if needed)
		Node other = new Node();
		other.setDepth(parent.getDepth() + 1);
		other.setParent(parent);
		other.setType("text");
		if (realEnd + 2 > text.length())
			other.setText("");
		else 
			other.setText(text.substring(realEnd + 2, text.length()));
		if (other.getText().length() != 0)
			parent.insertChild(other, childPos + 2);
	}
	
	private static int curpos;
	private static int goTree(Node n, int start, int end, Requality r)
	{
		if (n.getType().equals("text"))
		{
			// it is text, we should decide if we want to skip it
			String text = TextOps.regTransform(n.getText());
			if (curpos + text.length() < start)
			{
				// skip
				curpos += text.length() + 1;
				return 0; // locations not created
			} else
			{
				if (end <= curpos + text.length() + 1)
				{
					// end is also in this text -> create only one location
					createLocation(n, start - curpos, end - curpos, r);
					return 2; // locations created
				} else
				{
					// end is not in this text
					createLocation(n, start - curpos, text.length(), r);
					curpos += text.length() + 1;
					return 1; // not all locations created
				}
			}
		} else
		{
			int x = 0;
			for (int i = 0; i < n.getChildren().size(); i++)
			{
				x = goTree(n.getChildren().get(i), start, end, r);
				if (x == 1)
					start = curpos + 1;
				if (x == 2) 
					break;
			}
			return x;
		}
	}
	
	private static void restoreActualLocationInTree(Requality r, ActualLocation l)
	{
		// here is stored already transformed path
		ArrayList<Node> path = l.getPath();
		// cur is the last header
		int i = path.size() - 1;
		while (i >= 0)
		{
			if (path.get(i).getType().startsWith("h") || path.get(i).getType().equals("body"))
				break;
			i--;
		}
		Node cur = path.get(i);
		
		// go and see all cur's "text" children
		curpos = 0;
		goTree(cur, l.getPos(), l.getPos() + l.getText().length(), r);
	}
	public static void restoreLocationsInTree()
	{
		for (int i = 0; i < InterfaceOps.reqs2.size(); i++)
			for (int j = 0; j < InterfaceOps.reqs2.get(i).getActualLocationlist().size(); j++)
				restoreActualLocationInTree(InterfaceOps.reqs2.get(i), InterfaceOps.reqs2.get(i).getActualLocationlist().get(j));
	}
}
