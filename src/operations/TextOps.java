package operations;

import java.util.ArrayList;

import support.ActualLocation;
import support.Location;
import support.Node;
import support.Requality;

public class TextOps {
	public static String nodeTextExtract(Node n) // extracts text of some Node - Header or Requality
	{
		String res = "";
		ArrayList<Node> lst = n.getChildren();
		for (int i = 0; i < lst.size(); i++)
		{
			if (lst.get(i).getType().equals("text"))
			{
				if (!res.endsWith(" ") && !lst.get(i).getText().startsWith(" ") 
					&& !res.endsWith(",") && !lst.get(i).getText().startsWith(",") 
					&& !res.endsWith(".") && !lst.get(i).getText().startsWith(".")
					&& !res.endsWith(";") && !lst.get(i).getText().startsWith(";")
					&& !res.endsWith(":") && !lst.get(i).getText().startsWith(":")
					&& (!res.equals("")))
					res += " ";
				res += lst.get(i).getText();
			} else if (lst.get(i).getType().equals("requality"))
			{
				String s = nodeTextExtract(lst.get(i));
				if (!res.endsWith(" ") && !s.startsWith(" ") 
						&& !res.endsWith(",") && !s.startsWith(",") 
						&& !res.endsWith(".") && !s.startsWith(".")
						&& !res.endsWith(";") && !s.startsWith(";")
						&& !res.endsWith(":") && !s.startsWith(":")
						&& (!res.equals("")))
						res += " ";
				res += s;
			} else
			{
				break;
			}
		}
		return res;
	}
	
	public static String sectionTextExtract(Node n) // extracts text whole section - header and paragraphs
	{
		String res = "";
		ArrayList<Node> lst = n.getChildren();
		for (int i = 0; i < lst.size(); i++)
		{
			if (lst.get(i).getType().equals("text"))
			{
				if (!res.endsWith(" ") && !lst.get(i).getText().startsWith(" ") 
					&& !res.endsWith(",") && !lst.get(i).getText().startsWith(",") 
					&& !res.endsWith(".") && !lst.get(i).getText().startsWith(".")
					&& !res.endsWith(";") && !lst.get(i).getText().startsWith(";")
					&& !res.endsWith(":") && !lst.get(i).getText().startsWith(":")
					&& (!res.equals("")))
					res += " ";
				res += lst.get(i).getText();
			} else if (lst.get(i).getType().equals("requality"))
			{
				String s = sectionTextExtract(lst.get(i));
				if (!res.endsWith(" ") && !s.startsWith(" ") 
						&& !res.endsWith(",") && !s.startsWith(",") 
						&& !res.endsWith(".") && !s.startsWith(".")
						&& !res.endsWith(";") && !s.startsWith(";")
						&& !res.endsWith(":") && !s.startsWith(":")
						&& (!res.equals("")))
						res += " ";
				res += s;
			} else
			{
				String s = sectionTextExtract(lst.get(i));
				res += "\n" + s;
			}
		}
		return res;
	}

	public static void createActualLocations()
	{
		/// add actual location lists to requalities
		for (int i = 0; i < InterfaceOps.reqs1.size(); i++)
		{
			Requality curReq = InterfaceOps.reqs1.get(i);
			String curActualLocationText = "";
			Location prevLocation = null;
			for (int j = 0; j < curReq.getLocationlist().size(); j++)
			{
				Location curLocation = curReq.getLocationlist().get(j);
				if ((j != 0) && (prevLocation.getNode().getParent().equals(curLocation.getNode().getParent())) && (prevLocation.getChildNumber() + 1 == curLocation.getChildNumber()))
				{
					curActualLocationText += TextOps.nodeTextExtract(curLocation.getNode());
				} else if (j != 0)
				{
					ActualLocation temp = new ActualLocation(curActualLocationText);
					temp.setPath(InterfaceOps.getLocationPath(prevLocation));
					if (!temp.getText().equals(""))
						curReq.addActualLocation(temp);
					curActualLocationText = TextOps.nodeTextExtract(curLocation.getNode());
				} else
				{
					curActualLocationText += TextOps.nodeTextExtract(curLocation.getNode());
				}
				prevLocation = curLocation;
			}
			ActualLocation temp = new ActualLocation(curActualLocationText);
			temp.setPath(InterfaceOps.getLocationPath(prevLocation));
			if (!temp.getText().equals(""))
				curReq.addActualLocation(temp);
			
			//System.out.println("checked");
		}
	}
	
}
